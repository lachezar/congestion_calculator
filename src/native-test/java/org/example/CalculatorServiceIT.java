package org.example;

import io.quarkus.test.junit.QuarkusIntegrationTest;
import org.example.domain.CalculatorServiceTest;

@QuarkusIntegrationTest
public class CalculatorServiceIT extends CalculatorServiceTest {
    // Execute the same tests but in packaged mode.
}
