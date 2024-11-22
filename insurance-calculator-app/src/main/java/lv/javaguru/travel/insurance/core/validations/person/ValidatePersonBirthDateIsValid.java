package lv.javaguru.travel.insurance.core.validations.person;

import java.time.LocalDate;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lv.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import lv.javaguru.travel.insurance.core.api.dto.PersonDTO;
import lv.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import lv.javaguru.travel.insurance.core.util.DateTimeUtil;
import lv.javaguru.travel.insurance.core.validations.ValidationErrorFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class ValidatePersonBirthDateIsValid extends PersonFieldValidationImpl {

    private static final int MAX_AGE = 150;

    private final DateTimeUtil dateTimeUtil;
    private final ValidationErrorFactory validationErrorFactory;

    @Override
    public Optional<ValidationErrorDTO> validateSingle(AgreementDTO agreement, PersonDTO person) {
        LocalDate birthDate = person.personBirthDate();
        LocalDate currentDate = dateTimeUtil.currentDate();
        LocalDate minPossibleBirthDate = dateTimeUtil.subtractYears(currentDate, MAX_AGE);

        return (birthDate != null && (birthDate.isAfter(currentDate) || birthDate.isBefore(minPossibleBirthDate)))
                ? Optional.of(validationErrorFactory.buildError("ERROR_CODE_14"))
                : Optional.empty();
    }

}
