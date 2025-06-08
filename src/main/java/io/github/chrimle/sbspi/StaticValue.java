package io.github.chrimle.sbspi;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Similar to Spring's {@code Value}-annotation, this annotation can be applied to {@code static}
 * fields. This annotation supports <a
 * href="https://docs.spring.io/spring-framework/docs/3.0.x/reference/expressions.html">SpEL (Spring
 * Expression Language)</a> and/or <a
 * href="https://docs.spring.io/spring-framework/reference/core/beans/annotation-config/value-annotations.html">Spring
 * property placeholders</a>.
 *
 * <p>Example
 *
 * <pre>{@code
 * public class Example {
 *
 *   // Using Spring property placeholder
 *   @StaticValue("${my.custom.property}")
 *   private static String exampleWithoutDefault;
 *
 *   // Spring property placeholder with default value, defaults to "someDefault"
 *   @StaticValue("${my.custom.other-property:someDefault}")
 *   private static String exampleWithSomeDefault;
 * }
 * }</pre>
 *
 * <p>Note that actual processing of the {@code StaticValue} annotation is performed by {@link
 * StaticValueInjector}.
 *
 * @see org.springframework.beans.factory.annotation.Value
 * @author Chrimle
 * @since 0.1.0
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface StaticValue {
  /**
   * The value expression, such as {@code #{systemProperties.myProp}}, or property placeholder such
   * as {@code ${my.app.myProp}}.
   *
   * @return the value of this annotation.
   * @since 0.1.0
   */
  String value();
}
