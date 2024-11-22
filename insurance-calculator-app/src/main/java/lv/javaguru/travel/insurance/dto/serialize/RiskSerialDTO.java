package lv.javaguru.travel.insurance.dto.serialize;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RiskSerialDTO {

    private String riskIc;

    private BigDecimal premium;

}
