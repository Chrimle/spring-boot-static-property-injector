package io.github.chrimle.sbspi;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Class for testing {@link StaticValueInjector} is correctly resolving the values of the static fields of classes under test.
 */
@SpringBootTest(properties = {
    "test.strings.set-string-without-default=beta",
    "test.strings.set-string-with-default=delta"
})
@DisplayName("Testing `StaticValueInjector`")
class StaticValueInjectorTest {

  /**
   * Tests injecting values into {@code static} {@link String} fields of {@link StringFieldsHolder}.
   * @see StringFieldsHolder
   */
  @Nested
  @DisplayName("Testing `StringFieldsHolder`")
  class StringFieldsHolderTests {

    /**
     * @implNote The property <b>is not</b> set and a default value <b>is not</b> set.
     */
    @Test
    @DisplayName("Testing unset `String` without default resolves to `null`")
    void testUnsetStringWithoutDefault() {
      Assertions.assertNull(StringFieldsHolder.UNSET_STRING_WITHOUT_DEFAULT);
    }

    /**
     * @implNote The property <b>is not</b> set and a default value <b>is</b> set.
     */
    @Test
    @DisplayName("Testing unset `String` with default resolves to default")
    void testUnsetStringWithDefault() {
      Assertions.assertEquals("alpha", StringFieldsHolder.UNSET_STRING_WITH_DEFAULT);
    }

    /**
     * @implNote The property <b>is</b> set and a default value <b>is not</b> set.
     */
    @Test
    @DisplayName("Testing set `String` without default resolves to set value")
    void testSetStringWithoutDefault() {
      Assertions.assertEquals("beta", StringFieldsHolder.SET_STRING_WITHOUT_DEFAULT);
    }

    /**
     * @implNote The property <b>is</b> set and a default value <b>is</b> set.
     */
    @Test
    @DisplayName("Testing set `String` with default resolves to set value")
    void testSetStringWithDefault() {
      Assertions.assertEquals("delta", StringFieldsHolder.SET_STRING_WITH_DEFAULT);
    }
  }
}
