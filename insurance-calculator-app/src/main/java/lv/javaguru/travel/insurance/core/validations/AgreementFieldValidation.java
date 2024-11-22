package lv.javaguru.travel.insurance.core.validations;

import java.util.List;
import java.util.Optional;
import lv.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import lv.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;

public interface AgreementFieldValidation {

    Optional<ValidationErrorDTO> validateSingle(AgreementDTO agreement);

    List<ValidationErrorDTO> validateList(AgreementDTO agreement);

}
