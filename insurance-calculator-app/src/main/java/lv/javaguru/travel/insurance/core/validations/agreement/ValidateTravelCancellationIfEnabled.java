package lv.javaguru.travel.insurance.core.validations.agreement;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lv.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import lv.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import lv.javaguru.travel.insurance.core.validations.ValidationErrorFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class ValidateTravelCancellationIfEnabled extends AgreementFieldValidationImpl {

    private final ValidationErrorFactory validationErrorFactory;

    @Value("${feature.tripCancellation.enabled:true}")
    private Boolean isTripCancellationEnabled;

    @Override
    public Optional<ValidationErrorDTO> validateSingle(AgreementDTO agreement) {
        return isTripCancellationEnabled
                ? Optional.empty()
                : validateRisks(agreement);
    }

    private Optional<ValidationErrorDTO> validateRisks(AgreementDTO agreement) {
        return agreement.selectedRisks() != null && agreement.selectedRisks().contains("TRAVEL_CANCELLATION")
                ? Optional.of(validationErrorFactory.buildError("ERROR_CODE_61"))
                : Optional.empty();
    }

}
