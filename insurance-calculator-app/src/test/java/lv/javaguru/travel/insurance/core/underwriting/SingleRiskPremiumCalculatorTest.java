package lv.javaguru.travel.insurance.core.underwriting;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;
import lv.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import lv.javaguru.travel.insurance.core.api.dto.AgreementDTOTestBuilder;
import lv.javaguru.travel.insurance.core.api.dto.PersonDTO;
import lv.javaguru.travel.insurance.core.api.dto.PersonDTOTestBuilder;
import lv.javaguru.travel.insurance.core.api.dto.RiskDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SingleRiskPremiumCalculatorTest {

    @Mock
    private TravelRiskPremiumCalculator calculatorMock1;
    @Mock
    private TravelRiskPremiumCalculator calculatorMock2;
    @Mock
    private List<TravelRiskPremiumCalculator> riskPremiumCalculators;

    @InjectMocks
    private SingleRiskPremiumCalculator singleRiskCalculator;

    @BeforeEach
    void setUp() {
        when(riskPremiumCalculators.stream()).thenReturn(Stream.of(calculatorMock1, calculatorMock2));
        when(calculatorMock1.getRiskIc()).thenReturn("TRAVEL_CANCELLATION");
        when(calculatorMock2.getRiskIc()).thenReturn("TRAVEL_MEDICAL");
    }

    @Test
    void calculatePremiumShouldReturnRiskPremium() {
        AgreementDTO agreement = AgreementDTOTestBuilder.createAgreement().build();
        PersonDTO person = PersonDTOTestBuilder.createPerson().build();
        String riskIc = "TRAVEL_MEDICAL";

        when(calculatorMock2.calculateRiskPremium(agreement, person)).thenReturn(BigDecimal.ONE);

        RiskDTO result = singleRiskCalculator.calculatePremium(riskIc, agreement, person);

        assertThat(result)
                .satisfies(r -> {
                    assertThat(r.riskIc()).isEqualTo("TRAVEL_MEDICAL");
                    assertThat(r.premium()).isEqualTo(BigDecimal.ONE);
                });
    }

    @Test
    void calculatePremiumThrowsRuntimeExceptionWhenOnlyNotSupportedRisk() {
        AgreementDTO agreement = AgreementDTOTestBuilder.createAgreement().build();
        PersonDTO person = PersonDTOTestBuilder.createPerson().build();
        String riskIc = "NOT_SUPPORTED_RISK";

        assertThatThrownBy(() -> singleRiskCalculator.calculatePremium(riskIc, agreement, person))
                .isInstanceOf(RuntimeException.class);
    }

}
