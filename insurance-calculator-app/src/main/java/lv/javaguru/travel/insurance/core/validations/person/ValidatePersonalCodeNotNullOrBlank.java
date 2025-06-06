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
class ValidatePersonalCodeNotNullOrBlank extends PersonFieldValidationImpl {

    private final ValidationErrorFactory validationErrorFactory;

    @Override
    public Optional<ValidationErrorDTO> validateSingle(AgreementDTO agreement, PersonDTO person) {
        return (person.personalCode() == null || person.personalCode().isBlank())
                ? Optional.of(validationErrorFactory.buildError("ERROR_CODE_9"))
                : Optional.empty();
    }

}
