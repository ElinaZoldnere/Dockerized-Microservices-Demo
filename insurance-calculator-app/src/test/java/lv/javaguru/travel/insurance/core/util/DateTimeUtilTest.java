package lv.javaguru.travel.insurance.core.util;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("checkstyle:MagicNumber")
class DateTimeUtilTest {

    private final DateTimeUtil timeUtil = new DateTimeUtil();

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
