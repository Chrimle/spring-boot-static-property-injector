package io.github.chrimle.sbspi;

import java.lang.reflect.Field;
import java.util.Objects;
import java.util.Optional;

/**
 * Exception thrown by {@link StaticValueInjector} when processing {@link StaticValue}-annotations.
 *
 * <p><strong>API Note:</strong> This class cannot be instantiated by any constructor. Instead, it
 * can only be instantiated via <i>static factory methods</i>.
 *
 * @author Chrimle
 * @see #classNotFound(String, Throwable)
 * @see #emptyAnnotationValue(Field, String)
 * @see #unsupportedAnnotationValue(Field, String)
 * @see #unparsableAnnotationValue(Field, String, Throwable)
 * @since 0.1.0
 */
public class StaticValueInjectorException extends RuntimeException {

  private static final long serialVersionUID = 42L;

  private final Reason reason;
  private final Class<?> annotatedClass;
  private final String annotatedField;
  private final String annotationValue;

  private StaticValueInjectorException(
      final Reason reason,
      final Field field,
      final String annotationValue,
      final String message,
      final Throwable cause) {

    super(message, cause);
    this.reason = Objects.requireNonNull(reason);
    this.annotatedClass = Optional.ofNullable(field).map(Field::getDeclaringClass).orElse(null);
    this.annotatedField = Optional.ofNullable(field).map(Field::getName).orElse(null);
    this.annotationValue = annotationValue;
  }

  static StaticValueInjectorException classNotFound(final String className, final Throwable cause) {
    return new StaticValueInjectorException(
        Reason.CLASS_NOT_FOUND,
        null,
        null,
        "Could not load class '%s' while scanning for @StaticValue".formatted(className),
        cause);
  }

  static StaticValueInjectorException emptyAnnotationValue(
      final Field field, final String annotationValue) {
    return new StaticValueInjectorException(
        Reason.ANNOTATION_VALUE_EMPTY,
        field,
        annotationValue,
        "Field '%s.%s' is annotated with @StaticValue but its 'value' is empty"
            .formatted(field.getDeclaringClass().getSimpleName(), field.getName()),
        null);
  }

  static StaticValueInjectorException unsupportedAnnotationValue(
      final Field field, final String annotationValue) {
    return new StaticValueInjectorException(
        Reason.ANNOTATION_VALUE_UNSUPPORTED,
        field,
        annotationValue,
        "Field '%s.%s' is annotated with @StaticValue but its 'value' is unsupported (does not start with $ or #)"
            .formatted(field.getDeclaringClass().getSimpleName(), field.getName()),
        null);
  }

  static StaticValueInjectorException unparsableAnnotationValue(
      final Field field, final String annotationValue, final Throwable cause) {
    return new StaticValueInjectorException(
        Reason.ANNOTATION_VALUE_UNPARSABLE,
        field,
        annotationValue,
        "Field '%s.%s' is annotated with @StaticValue but the new value cannot be parsed to %s"
            .formatted(
                field.getDeclaringClass().getSimpleName(),
                field.getName(),
                field.getType().getSimpleName()),
        cause);
  }

  public String getAnnotationValue() {
    return annotationValue;
  }

  public String getAnnotatedField() {
    return annotatedField;
  }

  public Class<?> getAnnotatedClass() {
    return annotatedClass;
  }

  public Reason getReason() {
    return reason;
  }

  /**
   * The <i>reason</i> for the {@link StaticValueInjectorException} being thrown.
   *
   * @author Chrimle
   * @since 0.1.0
   */
  public enum Reason {
    /**
     * The {@link StaticValue#value()} was empty.
     *
     * @since 0.1.0
     */
    ANNOTATION_VALUE_EMPTY,
    /**
     * The {@link StaticValue#value()} could not be parsed.
     *
     * @since 0.1.0
     */
    ANNOTATION_VALUE_UNPARSABLE,
    /**
     * The {@link StaticValue#value()} was in an unsupported format.
     *
     * @since 0.1.0
     */
    ANNOTATION_VALUE_UNSUPPORTED,
    /**
     * The <i>class</i> annotated with {@link StaticValue} could not be found.
     *
     * @since 0.1.0
     */
    CLASS_NOT_FOUND
  }
}
