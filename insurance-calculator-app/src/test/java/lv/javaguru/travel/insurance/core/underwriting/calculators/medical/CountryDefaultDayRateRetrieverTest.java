package lv.javaguru.travel.insurance.core.underwriting.calculators.medical;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;
import lv.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import lv.javaguru.travel.insurance.core.api.dto.AgreementDTOTestBuilder;
import lv.javaguru.travel.insurance.core.domain.medical.CountryDefaultDayRate;
import lv.javaguru.travel.insurance.core.repositories.medical.CountryDefaultDayRateRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CountryDefaultDayRateRetrieverTest {

    @Mock
    private CountryDefaultDayRateRepository countryDefaultDayRateRepositoryMock;

    @InjectMocks
    private CountryDefaultDayRateRetriever countryDefaultDayRateRetriever;

    @Test
    void findCountryDefaultDayRateShouldFindRateWhenRateExists() {
        BigDecimal countryDefaultDayRate = BigDecimal.valueOf(2.5);
        AgreementDTO agreement = AgreementDTOTestBuilder.createAgreement().build();

        CountryDefaultDayRate countryDefaultDayRateMock = mock(CountryDefaultDayRate.class);
        when(countryDefaultDayRateRepositoryMock.findByCountryIc(any()))
                .thenReturn(Optional.of(countryDefaultDayRateMock));
        when(countryDefaultDayRateMock.getDefaultDayRate()).thenReturn(countryDefaultDayRate);

        BigDecimal actualDayRate = countryDefaultDayRateRetriever.findCountryDefaultDayRate(agreement);

        assertThat(actualDayRate).isEqualTo(countryDefaultDayRate);
    }

    @Test
    void findCountryDefaultDayRateShouldThrowExceptionWhenRateDoesNotExist() {
        AgreementDTO agreement = AgreementDTOTestBuilder.createAgreement()
                .withCountry("INVALID")
                .build();

        when(countryDefaultDayRateRepositoryMock.findByCountryIc("INVALID"))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> countryDefaultDayRateRetriever.findCountryDefaultDayRate(agreement))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Country ic = INVALID default day rate not found!");
    }

}
