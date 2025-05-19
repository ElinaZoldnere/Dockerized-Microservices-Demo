package lv.javaguru.travel.insurance.core.util;

import java.time.Clock;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DateTimeUtil {

    private final Clock defaultClock;

    public long calculateDifferenceBetweenDatesInDays(LocalDate dateFrom, LocalDate dateTo) {
        return ChronoUnit.DAYS.between(dateFrom, dateTo);
    }

    public int calculateDifferenceBetweenDatesInYears(LocalDate dateFrom, LocalDate dateTo) {
        Period period = Period.between(dateFrom, dateTo);
        return period.getYears();
    }

    public LocalDate currentDate() { // today 00:00:00 EET
        return LocalDate.now(defaultClock);
    }

    public LocalDate subtractYears(LocalDate date, int years) {
        return date.minusYears(years);
    }

}
