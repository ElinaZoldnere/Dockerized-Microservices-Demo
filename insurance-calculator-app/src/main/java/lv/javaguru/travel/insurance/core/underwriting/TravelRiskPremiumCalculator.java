package lv.javaguru.travel.insurance.core.underwriting;

import java.math.BigDecimal;
import lv.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import lv.javaguru.travel.insurance.core.api.dto.PersonDTO;

public interface TravelRiskPremiumCalculator {

    BigDecimal calculateRiskPremium(AgreementDTO agreement, PersonDTO person);

    String getRiskIc();

}
