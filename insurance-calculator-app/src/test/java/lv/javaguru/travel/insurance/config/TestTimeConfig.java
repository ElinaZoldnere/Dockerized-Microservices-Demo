package lv.javaguru.travel.insurance.config;

import java.time.Clock;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@TestConfiguration
public class TestTimeConfig {

    @Bean
    @Primary
    public Clock testClock() {
        ZoneId zone = ZoneId.of("Europe/Riga");
        LocalDate today = LocalDate.now(Clock.system(zone));
        LocalDate simulatedDate = today.withYear(2024);

        long daysOffset = ChronoUnit.DAYS.between(today, simulatedDate);
        Duration offset = Duration.ofDays(daysOffset);

        // Provides a ticking Clock shifted to the same day and month in the year 2024.
        return Clock.offset(Clock.system(zone), offset);
    }

}
