package lv.javaguru.travel.insurance.core.underwriting.calculators.cancellation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;
import lv.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import lv.javaguru.travel.insurance.core.api.dto.AgreementDTOTestBuilder;
import lv.javaguru.travel.insurance.core.domain.cancellation.TCCountrySafetyRatingCoefficient;
import lv.javaguru.travel.insurance.core.repositories.cancellation.TCCountrySafetyRatingCoefficientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TCCountrySafetyRatingCoefficientRetrieverTest {

    @Mock
    private TCCountrySafetyRatingCoefficientRepository repositoryMock;

    @InjectMocks
    private TCCountrySafetyRatingCoefficientRetriever retriever;

    @Test
    void findCountrySafetyRatingCoefficientShouldFindCoefficientWhenCoefficientExists() {
        BigDecimal coefficient = BigDecimal.valueOf(8);
        AgreementDTO agreement = AgreementDTOTestBuilder.createAgreement()
                .withCountry("SPAIN")
                .build();

        TCCountrySafetyRatingCoefficient coefficientMock = mock(TCCountrySafetyRatingCoefficient.class);
        when(repositoryMock.findCoefficientByCountryIc(any()))
                .thenReturn(Optional.of(coefficientMock));
        when(coefficientMock.getCoefficient()).thenReturn(coefficient);

        BigDecimal actualCoefficient = retriever.findCountrySafetyRatingCoefficient(agreement);

        assertThat(actualCoefficient).isEqualTo(coefficient);
    }

    @Test
    void findCountrySafetyRatingCoefficientShouldThrowExceptionWhenCoefficientDoesNotExist() {
        AgreementDTO agreement = AgreementDTOTestBuilder.createAgreement()
                .withCountry("INVALID")
                .build();

        when(repositoryMock.findCoefficientByCountryIc(any()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> retriever.findCountrySafetyRatingCoefficient(agreement))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Coefficient for country = INVALID not found!");
    }

}
