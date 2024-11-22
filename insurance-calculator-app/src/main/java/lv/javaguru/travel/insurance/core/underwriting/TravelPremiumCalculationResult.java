package lv.javaguru.travel.insurance.core.underwriting;

import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lv.javaguru.travel.insurance.core.api.dto.RiskDTO;

@Getter
@AllArgsConstructor
public class TravelPremiumCalculationResult {

    private BigDecimal agreementPremium;

    private List<RiskDTO> risks;

}
