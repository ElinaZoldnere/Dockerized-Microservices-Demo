package lv.javaguru.travel.insurance.core.underwriting.calculators.medical;

import java.math.BigDecimal;
import java.math.RoundingMode;
import lombok.RequiredArgsConstructor;
import lv.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import lv.javaguru.travel.insurance.core.api.dto.PersonDTO;
import lv.javaguru.travel.insurance.core.underwriting.TravelRiskPremiumCalculator;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class TravelMedicalRiskPremiumCalculator implements TravelRiskPremiumCalculator {

    private final DayCountCalculator dayCountCalculator;
    private final CountryDefaultDayRateRetriever countryDefaultDayRateRetriever;
    private final TMAgeCoefficientRetriever ageCoefficientRetriever;
    private final MedicalRiskLimitLevelCoefficientRetriever limitLevelCoefficientRetriever;

    @Override
    public BigDecimal calculateRiskPremium(AgreementDTO agreement, PersonDTO person) {
        BigDecimal dayCount = dayCountCalculator.calculateDayCount(agreement);
        BigDecimal countryDefaultDayRate = countryDefaultDayRateRetriever.findCountryDefaultDayRate(agreement);
        BigDecimal ageCoefficient = ageCoefficientRetriever.setAgeCoefficient(person);
        BigDecimal limitLevelCoefficient = limitLevelCoefficientRetriever.setLimitLevelCoefficient(person);
        return countryDefaultDayRate
                .multiply(dayCount)
                .multiply(ageCoefficient)
                .multiply(limitLevelCoefficient)
                .setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public String getRiskIc() {
        return "TRAVEL_MEDICAL";
    }

}
