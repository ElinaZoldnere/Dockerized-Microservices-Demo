package lv.javaguru.travel.insurance.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

/**
 * Annotation for standard integration tests using the shared Spring application context.
 * Includes autoconfigured MockMvc and a test clock that mirrors real time but
 * shifts the calendar to the year 2024.
 * <p>
 * Because the clock always reports the current day and time as if it were 2024,
 * chosen test dates that are considered to be valid must be valid for every moment within
 * the year 2024.
 * <p>
 * Tests that override properties, replace beans, or otherwise alter the context must use a
 * separate configuration strategy.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest
@AutoConfigureMockMvc
@Import(TestTimeConfig.class)
public @interface SharedIntegrationTest {

}
