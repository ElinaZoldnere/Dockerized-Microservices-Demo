package lv.javaguru.black.list.core.validations;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lv.javaguru.black.list.core.api.dto.PersonDTO;
import lv.javaguru.black.list.core.api.dto.ValidationErrorDTO;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class BlackListedPersonFieldValidationImpl implements BlackListedPersonFieldValidation {

    final List<ValidatePersonField> fieldValidations;

    @Override
    public List<ValidationErrorDTO> validate(PersonDTO person) {
        return fieldValidations.stream()
                .map(validation -> validation.validate(person))
                .flatMap(Optional::stream)
                .toList();
    }

}
