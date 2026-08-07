package lv.javaguru.travel.insurance.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lv.javaguru.travel.insurance.dto.util.BigDecimalSerializer;
import tools.jackson.databind.annotation.JsonSerialize;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RiskPremium {

    private String riskIc;

    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal premium;

}
