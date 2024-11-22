package lv.javaguru.travel.insurance.core.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lv.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import lv.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class CalculateAndUpdateAgreementWithPremiums {

    private final CalculateAndUpdatePersonsWithRiskPremiums calculateAndUpdatePersons;
    private final CalculateTotalAgreementPremium calculateTotalPremium;

    AgreementDTO calculateAgreementPremiums(AgreementDTO agreement) {
        List<PersonDTO> personsWithRiskPremiums =
                calculateAndUpdatePersons.calculateRiskPremiumsForAllPersons(agreement);
        BigDecimal totalAgreementPremium =
                calculateTotalPremium.calculateTotalAgreementPremium(personsWithRiskPremiums);
        String uuid = UUID.randomUUID().toString();
        return agreement.withPremiums(personsWithRiskPremiums, totalAgreementPremium, uuid);
    }

}
