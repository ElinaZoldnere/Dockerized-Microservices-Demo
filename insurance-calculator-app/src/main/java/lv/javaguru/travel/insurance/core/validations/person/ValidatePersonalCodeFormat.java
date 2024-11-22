package lv.javaguru.travel.insurance.core.validations.person;

import java.util.Optional;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import lv.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import lv.javaguru.travel.insurance.core.api.dto.PersonDTO;
import lv.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import lv.javaguru.travel.insurance.core.validations.ValidationErrorFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class ValidatePersonalCodeFormat extends PersonFieldValidationImpl {

    /**
     * Regex to validate the personal code format:<br>
     * \d{6}     Exactly six digits<br>
     * -         A hyphen character<br>
     * \d{5}     Exactly five digits
     */
    private static final String PERSONAL_CODE_REGEX = "\\d{6}-\\d{5}";

    private final ValidationErrorFactory validationErrorFactory;

    @Override
    public Optional<ValidationErrorDTO> validateSingle(AgreementDTO agreement, PersonDTO person) {
        return (person.personalCode() != null && !person.personalCode().isBlank())
                ? validatePersonalCode(person)
                : Optional.empty();
    }

    private Optional<ValidationErrorDTO> validatePersonalCode(PersonDTO person) {
        return (!Pattern.matches(PERSONAL_CODE_REGEX, person.personalCode()))
                ? Optional.of(validationErrorFactory.buildError("ERROR_CODE_15"))
                : Optional.empty();
    }

}
