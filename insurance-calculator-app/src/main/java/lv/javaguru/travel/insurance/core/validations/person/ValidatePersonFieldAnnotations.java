package lv.javaguru.travel.insurance.core.validations.person;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lv.javaguru.travel.insurance.core.api.dto.PersonDTO;
import lv.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import lv.javaguru.travel.insurance.core.util.Placeholder;
import lv.javaguru.travel.insurance.core.validations.ValidationErrorFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class ValidatePersonFieldAnnotations extends PersonFieldValidationImpl {

    private final Validator validator;
    private final ValidationErrorFactory validationErrorFactory;

    @Override
    public List<ValidationErrorDTO> validateList(PersonDTO person) {
        List<ValidationErrorDTO> errors = new ArrayList<>();

        Set<ConstraintViolation<PersonDTO>> violations = validator.validate(person);
        for (ConstraintViolation<PersonDTO> violation : violations) {

            String customMessage = violation.getMessage();
            Placeholder messagePlaceH = new Placeholder(
                    "ANNOTATION VALIDATION CUSTOM MESSAGE", customMessage);

            ValidationErrorDTO error = validationErrorFactory.buildError(
                    "ERROR_CODE_71", List.of(messagePlaceH));
            errors.add(error);
        }
        return errors;
    }

}
