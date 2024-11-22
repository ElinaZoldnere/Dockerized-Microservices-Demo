package lv.javaguru.travel.insurance.core.underwriting.calculators.medical;

import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import lv.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import lv.javaguru.travel.insurance.core.domain.medical.CountryDefaultDayRate;
import lv.javaguru.travel.insurance.core.repositories.medical.CountryDefaultDayRateRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class CountryDefaultDayRateRetriever {

    private final CountryDefaultDayRateRepository countryDefaultDayRateRepository;

    BigDecimal findCountryDefaultDayRate(AgreementDTO agreement) {
        String countryIc = agreement.country();
        return countryDefaultDayRateRepository.findByCountryIc(countryIc)
                .map(CountryDefaultDayRate::getDefaultDayRate)
                .orElseThrow(() -> new RuntimeException("Country ic = " + countryIc + " default day rate not found!"));
    }

}
