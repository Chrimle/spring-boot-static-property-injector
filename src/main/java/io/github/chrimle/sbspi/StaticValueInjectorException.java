package io.github.chrimle.sbspi;

public class StaticValueInjectorException extends RuntimeException {

  public StaticValueInjectorException(final String message) {
    super(message);
  }

  public StaticValueInjectorException(final String message, final Throwable cause) {
    super(message, cause);
  }
}
