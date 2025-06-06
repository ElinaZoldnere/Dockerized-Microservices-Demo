package lv.javaguru.travel.insurance.core.underwriting.calculators.medical;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import lv.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import lv.javaguru.travel.insurance.core.api.dto.AgreementDTOTestBuilder;
import lv.javaguru.travel.insurance.core.util.DateTimeUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DayCountCalculatorTest {

    @Mock
    private DateTimeUtil dateTimeUtilMock;

    @InjectMocks
    private DayCountCalculator dayCountCalculator;

    @Test
    void calculateDayCountShouldCalculateCorrectResult() {
        AgreementDTO agreement = AgreementDTOTestBuilder.createAgreement().build();
        when(dateTimeUtilMock.calculateDifferenceBetweenDatesInDays(any(), any())).thenReturn(1L);

        BigDecimal expectedDayCount = BigDecimal.ONE;
        BigDecimal actualDayCount = dayCountCalculator.calculateDayCount(agreement);

        assertThat(actualDayCount).isEqualTo(expectedDayCount);
    }

}
