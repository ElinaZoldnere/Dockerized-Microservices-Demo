package lv.javaguru.travel.insurance.core.api.dto;

import java.math.BigDecimal;

public class RiskDTOTestBuilder {
    private String riskIc;
    private BigDecimal premium;

    public static RiskDTOTestBuilder createRisk() {
        return new RiskDTOTestBuilder();
    }

    public RiskDTO build() {
        return new RiskDTO(
                riskIc,
                premium);
    }

    public RiskDTOTestBuilder withRiskIc(String riskIc) {
        this.riskIc = riskIc;
        return this;
    }

    public RiskDTOTestBuilder withPremium(BigDecimal premium) {
        this.premium = premium;
        return this;
    }

}
