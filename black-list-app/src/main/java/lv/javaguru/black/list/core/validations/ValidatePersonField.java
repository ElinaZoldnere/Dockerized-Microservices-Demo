package lv.javaguru.black.list.core.validations;

import java.util.Optional;
import lv.javaguru.black.list.core.api.dto.PersonDTO;
import lv.javaguru.black.list.core.api.dto.ValidationErrorDTO;

interface ValidatePersonField {

    Optional<ValidationErrorDTO> validate(PersonDTO person);

}
