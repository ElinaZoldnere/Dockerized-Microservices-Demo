package lv.javaguru.travel.insurance.core.services;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lv.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import lv.javaguru.travel.insurance.core.api.dto.PersonDTO;
import lv.javaguru.travel.insurance.core.underwriting.TravelCalculatePremiumUnderwriting;
import lv.javaguru.travel.insurance.core.underwriting.TravelPremiumCalculationResult;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class CalculateAndUpdatePersonsWithRiskPremiums {

    private final TravelCalculatePremiumUnderwriting calculateUnderwriting;

    List<PersonDTO> calculateRiskPremiumsForAllPersons(AgreementDTO agreement) {
        return agreement.persons().stream()
                .map(person -> calculateRiskPremiumsForOnePerson(agreement, person))
                .toList();
    }

    private PersonDTO calculateRiskPremiumsForOnePerson(AgreementDTO agreement, PersonDTO person) {
        TravelPremiumCalculationResult calculationResult =
                calculateUnderwriting.calculateAgreementPremium(agreement, person);
        return person.withRisks(calculationResult.getRisks());
    }

}
