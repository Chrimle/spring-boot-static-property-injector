package io.github.chrimle.sbspi;

/**
 * Exception thrown by {@link StaticValueInjector} when processing {@link StaticValue}-annotations.
 *
 * @since 0.1.0
 * @author Chrimle
 */
public class StaticValueInjectorException extends RuntimeException {

  /**
   * Exception thrown by {@link StaticValueInjector} when processing {@link
   * StaticValue}-annotations.
   *
   * @param message describing the issue in more detail.
   * @since 0.1.0
   */
  public StaticValueInjectorException(final String message) {
    super(message);
  }

  /**
   * Exception thrown by {@link StaticValueInjector} when processing {@link
   * StaticValue}-annotations.
   *
   * @param message describing the issue in more detail.
   * @param cause of this exception.
   * @since 0.1.0
   */
  public StaticValueInjectorException(final String message, final Throwable cause) {
    super(message, cause);
  }
}
