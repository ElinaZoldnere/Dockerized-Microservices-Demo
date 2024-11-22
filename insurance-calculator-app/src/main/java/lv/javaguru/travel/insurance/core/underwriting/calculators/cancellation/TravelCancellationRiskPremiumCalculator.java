package lv.javaguru.travel.insurance.core.underwriting.calculators.cancellation;

import java.math.BigDecimal;
import java.math.RoundingMode;
import lombok.RequiredArgsConstructor;
import lv.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import lv.javaguru.travel.insurance.core.api.dto.PersonDTO;
import lv.javaguru.travel.insurance.core.underwriting.TravelRiskPremiumCalculator;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class TravelCancellationRiskPremiumCalculator implements TravelRiskPremiumCalculator {

    private final TCAgeCoefficientRetriever ageCoefficientRetriever;
    private final TCCountrySafetyRatingCoefficientRetriever safetyRatingCoefficientRetriever;
    private final TCTravelCostCoefficientRetriever travelCostCoefficientRetriever;

    @Override
    public BigDecimal calculateRiskPremium(AgreementDTO agreement, PersonDTO person) {
        BigDecimal ageCoefficient = ageCoefficientRetriever.findAgeCoefficient(person);
        BigDecimal safetyRatingCoefficient =
                safetyRatingCoefficientRetriever.findCountrySafetyRatingCoefficient(agreement);
        BigDecimal travelCostCoefficient = travelCostCoefficientRetriever.findTravelCostCoefficient(person);
        return ageCoefficient
                .add(safetyRatingCoefficient)
                .add(travelCostCoefficient)
                .setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public String getRiskIc() {
        return "TRAVEL_CANCELLATION";
    }

}
