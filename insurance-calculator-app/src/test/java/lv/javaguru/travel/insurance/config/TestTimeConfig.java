package lv.javaguru.travel.insurance.config;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@TestConfiguration
public class TestTimeConfig {

    @Bean
    @Primary
    public Clock fixedClock() {
        LocalDate fixedDate = LocalDate.of(2025, 5, 19);
        ZoneId zoneId = ZoneId.of("Europe/Riga");
        Instant fixedInstant = fixedDate.atStartOfDay(zoneId).toInstant();
        return Clock.fixed(fixedInstant, zoneId);
    }

}
