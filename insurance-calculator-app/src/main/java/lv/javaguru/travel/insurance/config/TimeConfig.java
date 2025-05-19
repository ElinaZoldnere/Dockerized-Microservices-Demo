package lv.javaguru.travel.insurance.config;

import java.time.Clock;
import java.time.ZoneId;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TimeConfig {

    @Bean
    public Clock defaultClock() {
        return Clock.system(ZoneId.of("Europe/Riga"));
    }

}
