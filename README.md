# Spring Boot Static Property Injector

If you find this project useful, please ⭐ **Star** ⭐ it and share it with others!
This is the best way to show appreciation for this project - Thank you! ❤️

## Purpose
Load Spring Boot properties (as [SpEL](https://docs.spring.io/spring-framework/docs/3.0.x/reference/expressions.html) 
and/or Spring Property Placeholders e.g. [@Value-annotation](https://docs.spring.io/spring-framework/reference/core/beans/annotation-config/value-annotations.html)) 
to `static` fields!

## Example
The following is a simple example of how this library can be used.
### 1. Import Dependency
```xml
<dependency>
    <groupId>io.github.chrimle</groupId>
    <artifactId>spring-boot-static-property-injector</artifactId>
    <version>0.1.0</version>
</dependency>
```
### 2. Configure Bean Class Scanning using `sbspi.basePackage`
```properties
sbspi.basePackage=io.github.chrimle.example.subpackage
```
### 3. Annotate Fields with `@StaticValue`

```java
import io.github.chrimle.sbspi.StaticValue;

public class Example {

  // This field MAY remain null if the property is not set.
  @StaticValue("${myapp.custom.example-nullable-field}")
  private static String EXAMPLE_NULLABLE_FIELD;
  
  // This field MAY be set to "someDefaultValue" if the property is not set.
  @StaticValue("${myapp.custom.example-default-field:someDefaultValue}")
  private static String EXAMPLE_DEFAULT_FIELD;

}
```
### 4. If You encounter any issues
1. For unexpected issues, a `StaticValueInjectorException` will be thrown with a description of what went wrong.
2. In case of fields not being set as expected, review the `sbspi.basePackage`-property and the package name of the relevant class.
3. Enable `DEBUG` logs for `io.github.chrimle.sbspi` to see which fields have been processed.
4. Open a GitHub issue describing the issue and include the findings from these steps.

## Road Map
- GitHub Packages
- Maven Central Publishing
- Extend Test Suite
  - Test more rigorously
    - String
    - Integer
    - Boolean
- Support annotating `static` method arguments with `@StaticValue`
- Support property prefixes on class-level
  - Useful when annotating multiple fields with long common property prefixes.
- Improve `DEBUG` logs by masking potential secrets
  - Add `isSecret`-property to the `@StaticValue` annotation to mask property values in logs.
- and more to come...

