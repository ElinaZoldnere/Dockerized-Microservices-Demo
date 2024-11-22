package lv.javaguru.travel.insurance.core.underwriting;

import java.math.BigDecimal;
import java.util.List;
import lv.javaguru.travel.insurance.core.api.dto.RiskDTO;
import org.springframework.stereotype.Component;

@Component
class TotalRiskPremiumCalculator {

    public BigDecimal calculatePremium(List<RiskDTO> riskPremiums) {
        return riskPremiums.stream()
                .map(RiskDTO::premium)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
