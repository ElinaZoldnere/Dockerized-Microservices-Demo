package lv.javaguru.travel.insurance.core.underwriting.calculators.cancellation;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;
import lv.javaguru.travel.insurance.core.api.dto.PersonDTO;
import lv.javaguru.travel.insurance.core.api.dto.PersonDTOTestBuilder;
import lv.javaguru.travel.insurance.core.domain.cancellation.TCTravelCostCoefficient;
import lv.javaguru.travel.insurance.core.repositories.cancellation.TCTravelCostCoefficientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TCTravelCostCoefficientRetrieverTest {

    @Mock
    private TCTravelCostCoefficientRepository costCoefficientRepositoryMock;

    @InjectMocks
    private TCTravelCostCoefficientRetriever costCoefficientRetriever;

    @Test
    void findTravelCostPremiumShouldFindRateWhenTravelCostExists() {
        BigDecimal coefficient = BigDecimal.valueOf(30);
        PersonDTO person = PersonDTOTestBuilder.createPerson()
                .withTravelCost(BigDecimal.valueOf(6000))
                .build();

        TCTravelCostCoefficient travelCostCoefficientMock = mock(TCTravelCostCoefficient.class);
        when(costCoefficientRepositoryMock.findCoefficientByTravelCost(any()))
                .thenReturn(Optional.of(travelCostCoefficientMock));
        when(travelCostCoefficientMock.getCoefficient()).thenReturn(coefficient);

        BigDecimal actualTravelCostPremium = costCoefficientRetriever.findTravelCostCoefficient(person);

        assertThat(actualTravelCostPremium).isEqualTo(coefficient);
    }

    @Test
    void findTravelCostPremiumShouldThrowExceptionWhenTravelCostDoesNotExist() {
        PersonDTO person = PersonDTOTestBuilder.createPerson()
                .withTravelCost(BigDecimal.valueOf(10000000))
                .build();

        when(costCoefficientRepositoryMock.findCoefficientByTravelCost(BigDecimal.valueOf(10000000)))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> costCoefficientRetriever.findTravelCostCoefficient(person))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Coefficient for travel cost = 10000000 not found!");
    }

}
