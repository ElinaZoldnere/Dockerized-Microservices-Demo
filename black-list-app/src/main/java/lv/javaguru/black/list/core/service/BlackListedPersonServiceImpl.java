package lv.javaguru.black.list.core.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lv.javaguru.black.list.core.api.command.BlackListedPersonCoreCommand;
import lv.javaguru.black.list.core.api.command.BlackListedPersonCoreResult;
import lv.javaguru.black.list.core.api.command.BlackListedPersonCoreResultErrors;
import lv.javaguru.black.list.core.api.command.BlackListedPersonCoreResultSuccess;
import lv.javaguru.black.list.core.api.dto.PersonDTO;
import lv.javaguru.black.list.core.api.dto.ValidationErrorDTO;
import lv.javaguru.black.list.core.repositories.BlackListedPersonEntityRepository;
import lv.javaguru.black.list.core.validations.BlackListedPersonFieldValidation;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(access = lombok.AccessLevel.PACKAGE)
public class BlackListedPersonServiceImpl implements BlackListedPersonService {

    private final BlackListedPersonFieldValidation validation;
    private final BlackListedPersonEntityRepository repository;

    @Override
    public BlackListedPersonCoreResult checkPerson(BlackListedPersonCoreCommand command) {
        List<ValidationErrorDTO> errors = validation.validate(command.person());
        return errors.isEmpty()
                ? checkPersonInDatabase(command)
                : new BlackListedPersonCoreResultErrors(errors);
    }

    private BlackListedPersonCoreResult checkPersonInDatabase(BlackListedPersonCoreCommand command) {
        PersonDTO blackListedPerson = command.person();
        boolean isBlackListed = repository.findBy(
                blackListedPerson.personFirstName(),
                blackListedPerson.personLastName(),
                blackListedPerson.personalCode()
        ).isPresent();
        return new BlackListedPersonCoreResultSuccess(blackListedPerson, isBlackListed);
    }

}
