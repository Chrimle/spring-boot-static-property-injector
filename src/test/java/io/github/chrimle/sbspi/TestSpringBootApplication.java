package io.github.chrimle.sbspi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * This is a skeleton Spring Boot Application, which will scan and enable {@link
 * StaticValueInjector}.
 */
@SpringBootApplication(scanBasePackages = "io.github.chrimle.sbspi")
public class TestSpringBootApplication {
  public static void main(String[] args) {
    SpringApplication.run(TestSpringBootApplication.class, args);
  }
}
