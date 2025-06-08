package io.github.chrimle.sbspi;

/**
 * This is a class demonstrating how {@code static String} fields can be set
 * using properties.
 */
public class StringFieldsHolder {

  /**
   * This property is unset and has no default. The expected value is {@code null}.
   */
  @StaticValue("${test.strings.unset-string-without-default}")
  public static String UNSET_STRING_WITHOUT_DEFAULT;

  /**
   * This property is unset and has a default. The expected value is {@code "alpha"}.
   */
  @StaticValue("${test.strings.unset-string-with-default:alpha}")
  public static String UNSET_STRING_WITH_DEFAULT;

  /**
   * This property is set and has no default. The expected value is {@code beta}.
   */
  @StaticValue("${test.strings.set-string-without-default}")
  public static String SET_STRING_WITHOUT_DEFAULT;

  /**
   * This property is set and has a default. The expected value is {@code delta}.
   */
  @StaticValue("${test.strings.set-string-with-default:gamma}")
  public static String SET_STRING_WITH_DEFAULT;
}
