package lv.javaguru.travel.insurance.core.underwriting.calculators.cancellation;

import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import lv.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import lv.javaguru.travel.insurance.core.domain.cancellation.TCCountrySafetyRatingCoefficient;
import lv.javaguru.travel.insurance.core.repositories.cancellation.TCCountrySafetyRatingCoefficientRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class TCCountrySafetyRatingCoefficientRetriever {

    private final TCCountrySafetyRatingCoefficientRepository repository;

    BigDecimal findCountrySafetyRatingCoefficient(AgreementDTO agreement) {
        String countryIc = agreement.country();
        return repository.findCoefficientByCountryIc(countryIc)
                .map(TCCountrySafetyRatingCoefficient::getCoefficient)
                .orElseThrow(() -> new RuntimeException("Coefficient for country = " + countryIc + " not found!"));
    }

}
