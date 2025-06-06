package lv.javaguru.travel.insurance.core.underwriting;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;
import lv.javaguru.travel.insurance.core.api.dto.RiskDTO;
import lv.javaguru.travel.insurance.core.api.dto.RiskDTOTestBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TotalRiskPremiumCalculatorTest {

    @Mock
    private List<RiskDTO> listRiskMock;
    @InjectMocks
    private TotalRiskPremiumCalculator totalRiskCalculator;

    @Test
    void calculatePremiumShouldReturnCorrectResult() {
        RiskDTO risk1 = RiskDTOTestBuilder.createRisk()
                .withRiskIc("TRAVEL_MEDICAL")
                .withPremium(BigDecimal.ONE)
                .build();
        RiskDTO risk2 = RiskDTOTestBuilder.createRisk()
                .withRiskIc("TRAVEL_CANCELLATION")
                .withPremium(BigDecimal.TEN)
                .build();

        when(listRiskMock.stream()).thenReturn(Stream.of(risk1, risk2));

        BigDecimal result = totalRiskCalculator.calculatePremium(listRiskMock);

        assertThat(result).isEqualTo(BigDecimal.valueOf(11));
    }

}
