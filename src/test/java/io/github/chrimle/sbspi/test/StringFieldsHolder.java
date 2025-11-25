/*
  Copyright 2025 Chrimle

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.

*/

package io.github.chrimle.sbspi.test;

import io.github.chrimle.sbspi.StaticValue;

/** This is a class demonstrating how {@code static String} fields can be set using properties. */
public class StringFieldsHolder {

  /** This property is unset and has no default. The expected value is {@code null}. */
  @StaticValue("${test.strings.unset-string-without-default}")
  public static String UNSET_STRING_WITHOUT_DEFAULT;

  /** This property is unset and has a default. The expected value is {@code "alpha"}. */
  @StaticValue("${test.strings.unset-string-with-default:alpha}")
  public static String UNSET_STRING_WITH_DEFAULT;

  /** This property is set and has no default. The expected value is {@code "beta"}. */
  @StaticValue("${test.strings.set-string-without-default}")
  public static String SET_STRING_WITHOUT_DEFAULT;

  /** This property is set and has a default. The expected value is {@code "delta"}. */
  @StaticValue("${test.strings.set-string-with-default:gamma}")
  public static String SET_STRING_WITH_DEFAULT;

  /** This property is unset and has no default. The expected value is {@code null}. */
  @StaticValue("#{getProperty('test.strings.unset-spel-string-without-default')}")
  public static String UNSET_SPEL_STRING_WITHOUT_DEFAULT;

  /** This property is unset and has a default. The expected value is {@code "epsilon"} */
  @StaticValue("#{getProperty('test.strings.unset-spel-string-with-default') ?: 'epsilon'}")
  public static String UNSET_SPEL_STRING_WITH_DEFAULT;

  /** This property is set and has no default. The expected value is {@code "zeta"}. */
  @StaticValue("#{getProperty('test.strings.set-spel-string-without-default')}")
  public static String SET_SPEL_STRING_WITHOUT_DEFAULT;

  /** This property is set and has a default. The expected value is {@code "eta"}. */
  @StaticValue("#{getProperty('test.strings.set-spel-string-with-default') ?: 'theta'}")
  public static String SET_SPEL_STRING_WITH_DEFAULT;
}
