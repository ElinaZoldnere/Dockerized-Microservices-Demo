package lv.javaguru.travel.insurance.config;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestTimeConfig.class)
public class TestTimeConfigTest {

    @Autowired
    private Clock testClock;

    @Test
    void testTimeConfigShouldCorrectlyConfigureLocalDate() {
        ZoneId zone = ZoneId.of("Europe/Riga");
        LocalDate expected = LocalDate.now(zone).withYear(2024);
        LocalDate actual = LocalDate.now(testClock);

        assertThat(actual).isEqualTo(expected);
    }

}
