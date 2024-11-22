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
class TravelCalculatePremiumUnderwritingImpl implements TravelCalculatePremiumUnderwriting {

    private final SingleRiskPremiumCalculator singleRiskCalculator;
    private final TotalRiskPremiumCalculator totalRiskCalculator;

    @Override
    public TravelPremiumCalculationResult calculateAgreementPremium(AgreementDTO agreement, PersonDTO person) {
        List<RiskDTO> risks = calculateRiskPremiums(agreement, person);
        BigDecimal agreementPremium = totalRiskCalculator.calculatePremium(risks);
        return new TravelPremiumCalculationResult(agreementPremium, risks);
    }

    private List<RiskDTO> calculateRiskPremiums(AgreementDTO agreement, PersonDTO person) {
        return agreement.selectedRisks().stream()
                .map(riskIc -> singleRiskCalculator.calculatePremium(riskIc, agreement, person))
                .toList();
    }

}
