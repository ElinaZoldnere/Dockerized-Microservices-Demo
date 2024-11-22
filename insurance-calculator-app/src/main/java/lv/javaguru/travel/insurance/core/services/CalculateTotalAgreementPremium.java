package lv.javaguru.travel.insurance.core.services;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import lv.javaguru.travel.insurance.core.api.dto.PersonDTO;
import lv.javaguru.travel.insurance.core.api.dto.RiskDTO;
import org.springframework.stereotype.Component;

@Component
class CalculateTotalAgreementPremium {

    BigDecimal calculateTotalAgreementPremium(List<PersonDTO> personsWithRisks) {
        return personsWithRisks.stream()
                .map(PersonDTO::personRisks)
                .flatMap(Collection::stream)
                .map(RiskDTO::premium)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
