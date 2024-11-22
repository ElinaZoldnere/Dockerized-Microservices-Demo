package lv.javaguru.travel.insurance.core.validations;


import java.util.List;
import java.util.Optional;
import lv.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import lv.javaguru.travel.insurance.core.api.dto.PersonDTO;
import lv.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;

public interface PersonFieldValidation {

    Optional<ValidationErrorDTO> validateSingle(AgreementDTO agreement, PersonDTO person);

    List<ValidationErrorDTO> validateList(PersonDTO person);

}
