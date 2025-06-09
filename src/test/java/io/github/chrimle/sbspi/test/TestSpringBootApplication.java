package io.github.chrimle.sbspi.test;

import io.github.chrimle.sbspi.StaticValueInjector;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * This is a skeleton Spring Boot Application, which will scan and enable {@link
 * StaticValueInjector}.
 */
@SpringBootApplication(scanBasePackageClasses = StaticValueInjector.class)
public class TestSpringBootApplication {}
