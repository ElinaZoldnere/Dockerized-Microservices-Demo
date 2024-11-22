package lv.javaguru.travel.insurance.core.validations.person;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lv.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import lv.javaguru.travel.insurance.core.api.dto.PersonDTO;
import lv.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import lv.javaguru.travel.insurance.core.validations.ValidationErrorFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class ValidateMedicalRiskLimitLevelNotEmpty extends PersonFieldValidationImpl {

    private final ValidationErrorFactory validationErrorFactory;

    @Value("${medical.risk.limit.level.enabled:false}")
    private Boolean medicalRiskLimitLevelEnabled;

    @Override
    public Optional<ValidationErrorDTO> validateSingle(AgreementDTO agreement, PersonDTO person) {
        return (isMedicalRiskEnabled() && containsRisk("TRAVEL_MEDICAL", agreement)
                && (person.medicalRiskLimitLevel() == null || person.medicalRiskLimitLevel().isBlank()))
                ? Optional.of(validationErrorFactory.buildError("ERROR_CODE_8"))
                : Optional.empty();
    }

    private boolean isMedicalRiskEnabled() {
        return medicalRiskLimitLevelEnabled;
    }

    private boolean containsRisk(String riskType, AgreementDTO agreement) {
        if (agreement.selectedRisks() == null || agreement.selectedRisks().isEmpty()) {
            return false;
        }
        return agreement.selectedRisks().contains(riskType);
    }

}
