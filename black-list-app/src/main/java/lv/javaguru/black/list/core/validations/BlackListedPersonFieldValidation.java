package lv.javaguru.black.list.core.validations;

import java.util.List;
import lv.javaguru.black.list.core.api.dto.PersonDTO;
import lv.javaguru.black.list.core.api.dto.ValidationErrorDTO;

public interface BlackListedPersonFieldValidation {

    List<ValidationErrorDTO> validate(PersonDTO blackListedPersonDTO);

}
