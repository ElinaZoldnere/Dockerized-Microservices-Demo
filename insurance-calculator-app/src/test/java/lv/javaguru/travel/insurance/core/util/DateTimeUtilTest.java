package lv.javaguru.travel.insurance.core.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Clock;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

@SuppressWarnings("checkstyle:MagicNumber")
class DateTimeUtilTest {

    private final Clock testClock = Clock.systemUTC();
    private final DateTimeUtil timeUtil = new DateTimeUtil(testClock);

    @Test
    void calculateDifferenceBetweenDaysShouldCalculateCorrectResult() {
        LocalDate sampleDateFrom = LocalDate.of(2025, 3, 10);
        LocalDate sampleDateTo = LocalDate.of(2025, 3, 11);

        long difference = timeUtil.calculateDifferenceBetweenDatesInDays(sampleDateFrom, sampleDateTo);

        assertThat(difference).isEqualTo(1);
    }

    @Test
    void subtractYearsShouldCalculateCorrectResult() {
        LocalDate sampleDate = LocalDate.of(2025, 3, 10);

        LocalDate result = timeUtil.subtractYears(sampleDate, 150);

        assertThat(result.getYear()).isEqualTo(1875);
    }

}
