package lv.javaguru.travel.insurance.core.validations.person;

import com.fasterxml.jackson.databind.JsonMappingException;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lv.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import lv.javaguru.travel.insurance.core.api.dto.PersonDTO;
import lv.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import lv.javaguru.travel.insurance.core.blacklist.BlackListCheckPersonService;
import lv.javaguru.travel.insurance.core.blacklist.dto.BlackListCheckPersonRequest;
import lv.javaguru.travel.insurance.core.util.Placeholder;
import lv.javaguru.travel.insurance.core.validations.ValidationErrorFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(access = lombok.AccessLevel.PACKAGE)
public class ValidatePersonIsNotBlacklisted extends PersonFieldValidationImpl {

    private final BlackListCheckPersonService service;
    private final ValidationErrorFactory validationErrorFactory;

    @Override
    public Optional<ValidationErrorDTO> validateSingle(AgreementDTO agreement, PersonDTO person) {
        return personDataIsValid(person)
                ? checkPersonInBlacklist(person)
                : Optional.empty();
    }

    private boolean personDataIsValid(PersonDTO person) {
        return person.personFirstName() != null
                && !person.personFirstName().isBlank()
                && person.personLastName() != null
                && !person.personLastName().isBlank()
                && person.personalCode() != null
                && !person.personalCode().isBlank();
    }

    private Optional<ValidationErrorDTO> checkPersonInBlacklist(PersonDTO person) {
        String personFirstName = person.personFirstName();
        String personLastName = person.personLastName();
        String personalCode = person.personalCode();
        BlackListCheckPersonRequest request = new BlackListCheckPersonRequest(
                personFirstName,
                personLastName,
                personalCode);
        try {
            if (service.checkPerson(request)) {
                Placeholder placeholder1 = new Placeholder("PERSON_FIRST_NAME", personFirstName);
                Placeholder placeholder2 = new Placeholder("PERSON_LAST_NAME", personLastName);
                Placeholder placeholder3 = new Placeholder("PERSONAL_CODE", personalCode);
                return Optional.of(validationErrorFactory.buildError(
                        "ERROR_CODE_31", List.of(placeholder1, placeholder2, placeholder3)));
            } else {
                return Optional.empty();
            }
        } catch (JsonMappingException e) {
            return Optional.of(validationErrorFactory.buildError("ERROR_CODE_33"));
        } catch (Exception e) {
            return Optional.of(validationErrorFactory.buildError("ERROR_CODE_32"));
        }
    }

}
