# Spring Boot Static Property Injector

If you find this project useful, please ⭐ **Star** ⭐ it and share it with others!
This is the best way to show appreciation for this project - Thank you! ❤️

## Purpose
Load Spring Boot properties (as [SpEL](https://docs.spring.io/spring-framework/docs/3.0.x/reference/expressions.html) 
and/or Spring Property Placeholders e.g. [@Value-annotation](https://docs.spring.io/spring-framework/reference/core/beans/annotation-config/value-annotations.html)) 
to `static` fields!

## JavaDoc
[The official JavaDoc hosted on javadoc.io](https://javadoc.io/doc/io.github.chrimle/spring-boot-static-property-injector/latest/io/github/chrimle/sbspi/package-summary.html).

## Example
The following is a simple example of how this library can be used.
### 1. Import Dependency
```xml
<dependency>
    <groupId>io.github.chrimle</groupId>
    <artifactId>spring-boot-static-property-injector</artifactId>
    <version>0.1.1</version>
</dependency>
```
> [!NOTE]
> This artifact is hosted on
> - [Maven Central Repository](https://central.sonatype.com/artifact/io.github.chrimle/spring-boot-static-property-injector)
> - [GitHub Packages](https://github.com/Chrimle/spring-boot-static-property-injector/packages)

### 2. Include `StaticValueInjector` Bean

```java
import io.github.chrimle.sbspi.StaticValueInjector;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackageClasses = StaticValueInjector.class)
class ExampleSpringBootApplication {}
```

### 3. Configure Bean Class Scanning using `sbspi.basePackage`
```properties
sbspi.basePackage=your.example.app.subpackage
```
### 4. Annotate Fields with `@StaticValue`

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
### 5. If You encounter any issues
1. For unexpected issues, a `StaticValueInjectorException` will be thrown with a description of what went wrong.
2. In case of fields not being set as expected, review the `sbspi.basePackage`-property and the package name of the relevant class.
3. Enable `DEBUG` logs for `io.github.chrimle.sbspi` to see which fields have been processed.
4. Open a GitHub issue describing the issue and include the findings from these steps.

## Road Map

Plan for the 1.0.0 release.

### MAJOR
- TBD...

### MINOR
- Support annotating `static` method arguments with `@StaticValue`
- Support property prefixes on class-level
  - Useful when annotating multiple fields with long common property prefixes.
- Improve `DEBUG` logs by masking potential secrets
  - Add `isSecret`-property to the `@StaticValue` annotation to mask property values in logs.

### PATCH
- Extend Test Suite
  - Test more rigorously
    - String
    - Integer
    - Boolean

## Change Log
### 0.1.0
- Introduced `@StaticValue`
- Introduced `StaticValueInjector`
- Added Support for SpEL
- Added Support for Property Placeholders
- Published to GitHub Packages
- Published to Maven Central Repository
- Deployed GitHub Pages
- Deployed JavaDoc
