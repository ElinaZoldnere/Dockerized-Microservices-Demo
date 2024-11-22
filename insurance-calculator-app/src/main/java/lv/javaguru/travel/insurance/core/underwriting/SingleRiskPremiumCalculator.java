package lv.javaguru.travel.insurance.core.underwriting;

import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lv.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import lv.javaguru.travel.insurance.core.api.dto.PersonDTO;
import lv.javaguru.travel.insurance.core.api.dto.RiskDTO;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class SingleRiskPremiumCalculator {

    private final List<TravelRiskPremiumCalculator> riskPremiumCalculators;

    RiskDTO calculatePremium(String riskIc, AgreementDTO agreement, PersonDTO person) {
        var riskPremiumCalculator = findRiskPremiumCalculator(riskIc);
        BigDecimal premium = riskPremiumCalculator.calculateRiskPremium(agreement, person);
        return new RiskDTO(riskIc, premium);
    }

    private TravelRiskPremiumCalculator findRiskPremiumCalculator(String riskIc) {
        return riskPremiumCalculators.stream()
                .filter(riskCalculator -> riskCalculator.getRiskIc().equals(riskIc))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Not supported riskIc = " + riskIc));
    }

}
