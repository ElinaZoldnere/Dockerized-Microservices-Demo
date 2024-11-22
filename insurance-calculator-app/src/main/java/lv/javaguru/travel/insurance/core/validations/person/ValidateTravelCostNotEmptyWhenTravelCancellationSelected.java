package lv.javaguru.travel.insurance.core.validations.person;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lv.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import lv.javaguru.travel.insurance.core.api.dto.PersonDTO;
import lv.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import lv.javaguru.travel.insurance.core.validations.ValidationErrorFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class ValidateTravelCostNotEmptyWhenTravelCancellationSelected extends PersonFieldValidationImpl {

    private final ValidationErrorFactory validationErrorFactory;

    @Override
    public Optional<ValidationErrorDTO> validateSingle(AgreementDTO agreement, PersonDTO person) {
        return (containsRisk("TRAVEL_CANCELLATION", agreement) && person.travelCost() == null)
                ? Optional.of(validationErrorFactory.buildError("ERROR_CODE_10"))
                : Optional.empty();
    }

    private boolean containsRisk(String riskType, AgreementDTO agreement) {
        if (agreement.selectedRisks() == null || agreement.selectedRisks().isEmpty()) {
            return false;
        }
        return agreement.selectedRisks().contains(riskType);
    }

}
