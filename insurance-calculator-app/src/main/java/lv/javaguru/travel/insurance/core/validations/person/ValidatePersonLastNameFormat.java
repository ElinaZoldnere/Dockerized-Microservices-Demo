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
public class ValidatePersonLastNameFormat extends PersonFieldValidationImpl {

    /**
     * Regex to validate person last name:<br>
     * ^            Start of the string<br>
     * [A-Za-zĀČĒĢĪĶĻŅŠŪŽāčēģīķļņšūž]  Any uppercase or lowercase English or Latvian letter<br>
     * \s           A space character<br>
     * \-           A hyphen character<br>
     * +            One or more of the preceding characters<br>
     * $            End of the string
     */
    private static final String PERSON_LAST_NAME_REGEX = "^[A-Za-zĀČĒĢĪĶĻŅŠŪŽāčēģīķļņšūž\\s\\-]+$";

    private final ValidationErrorFactory validationErrorFactory;

    @Override
    public Optional<ValidationErrorDTO> validateSingle(AgreementDTO agreement, PersonDTO person) {
        return person.personLastName() != null && !person.personLastName().isBlank()
                ? validatePersonLastName(person)
                : Optional.empty();
    }

    private Optional<ValidationErrorDTO> validatePersonLastName(PersonDTO person) {
        return (!Pattern.matches(PERSON_LAST_NAME_REGEX, person.personLastName()))
                ? Optional.of(validationErrorFactory.buildError("ERROR_CODE_17"))
                : Optional.empty();
    }

}
