package io.github.chrimle.sbspi;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.core.convert.ConversionException;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

/**
 * Processes all classes within {@link #basePackage} and processes <em>static fields</em> annotated
 * with {@link StaticValue}.
 *
 * @since 0.1.0
 * @author Chrimle
 */
@Component
public class StaticValueInjector implements BeanPostProcessor, ApplicationContextAware {

  /**
   * The annotation class which to process.
   *
   * @since 0.1.0
   */
  public static final Class<StaticValue> STATIC_VALUE_CLASS = StaticValue.class;

  private static final Logger LOGGER = LoggerFactory.getLogger(StaticValueInjector.class);

  @Value("${sbspi.basePackage}")
  private String basePackage;

  private ApplicationContext context;
  private ClassPathScanningCandidateComponentProvider scanner;
  private final ConversionService conversionService = new DefaultConversionService();
  private final SpelExpressionParser parser = new SpelExpressionParser();
  private final StandardEvaluationContext evalContext = new StandardEvaluationContext();

  @Override
  public void setApplicationContext(final ApplicationContext applicationContext)
      throws BeansException {
    this.context = applicationContext;
    this.scanner = new ClassPathScanningCandidateComponentProvider(false);
    this.scanner.addIncludeFilter((metadataReader, metadataReaderFactory) -> true);
    ConfigurableListableBeanFactory beanFactory =
        ((ConfigurableApplicationContext) context).getBeanFactory();
    this.evalContext.setBeanResolver(new BeanFactoryResolver(Objects.requireNonNull(beanFactory)));
    this.evalContext.setRootObject(context.getEnvironment());
  }

  @Override
  public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
    try {
      injectStaticProperties();
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    }
    return bean;
  }

  private void injectStaticProperties() throws IllegalAccessException {
    final List<Field> allAnnotatedFields = getAllAnnotatedFields();

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug(
          "Found {} fields annotated with {}.",
          allAnnotatedFields.size(),
          STATIC_VALUE_CLASS.getSimpleName());
    }

    for (final Field field : allAnnotatedFields) {
      final var annotation = Objects.requireNonNull(field.getAnnotation(STATIC_VALUE_CLASS));
      String annotationValue = annotation.value();
      if (annotationValue == null || annotationValue.isBlank()) {
        throw new StaticValueInjectorException(
            "The field %s was annotated with %s, but 'value' is either null or blank!"
                .formatted(field.getName(), STATIC_VALUE_CLASS.getSimpleName()));
      }

      String value;
      if (annotationValue.startsWith("#{") && annotationValue.endsWith("}")) {
        annotationValue = annotationValue.substring(2, annotationValue.length() - 1);
        value =
            Optional.ofNullable(parser.parseExpression(annotationValue).getValue(evalContext))
                .map(Object::toString)
                .orElse(null);
      } else if (annotationValue.startsWith("${")) {
        value = context.getEnvironment().resolvePlaceholders(annotationValue);
      } else {
        throw new StaticValueInjectorException(
            "The field %s will not be assigned a new value, because the 'value' is neither a valid SpEL nor a valid Spring Boot Property Placeholder!"
                .formatted(field.getName()));
      }

      if (annotationValue.equals(value)) {
        if (LOGGER.isDebugEnabled()) {
          LOGGER.debug(
              "The field {} will not be assigned a new value, because the 'value' could not be resolved!",
              field.getName());
        }
        continue;
      }
      final Object convertedValue;
      try {
        convertedValue = conversionService.convert(value, field.getType());
      } catch (ConversionException e) {
        throw new StaticValueInjectorException(
            "The field {} cannot be assigned a new value, because ConversionService could not parse and convert the new value!",
            e);
      }

      final boolean accessBefore = field.canAccess(null);
      field.setAccessible(true);
      field.set(null, convertedValue);
      field.setAccessible(accessBefore);
      if (LOGGER.isDebugEnabled()) {
        LOGGER.debug("The field {} has been assigned a new value!", field.getName());
      }
    }
  }

  private List<Field> getAllAnnotatedFields() {
    return scanner.findCandidateComponents(basePackage).stream()
        .map(
            beanDefinition -> {
              try {
                return Class.forName(beanDefinition.getBeanClassName());
              } catch (ClassNotFoundException e) {
                throw new StaticValueInjectorException(
                    "Could not find the class for the bean: %s"
                        .formatted(beanDefinition.getBeanClassName()),
                    e);
              }
            })
        .map(Class::getDeclaredFields)
        .flatMap(Arrays::stream)
        .filter(field -> field.isAnnotationPresent(STATIC_VALUE_CLASS))
        .toList();
  }
}
