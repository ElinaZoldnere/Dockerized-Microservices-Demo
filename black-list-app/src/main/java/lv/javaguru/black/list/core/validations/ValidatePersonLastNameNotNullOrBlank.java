package lv.javaguru.black.list.core.validations;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lv.javaguru.black.list.core.api.dto.PersonDTO;
import lv.javaguru.black.list.core.api.dto.ValidationErrorDTO;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class ValidatePersonLastNameNotNullOrBlank implements ValidatePersonField {

    private final ValidationErrorFactory validationErrorFactory;

    @Override
    public Optional<ValidationErrorDTO> validate(PersonDTO person) {
        return (person.personLastName() == null || person.personLastName().isBlank())
                ? Optional.of(validationErrorFactory.buildError("ERROR_CODE_2"))
                : Optional.empty();
    }

}
