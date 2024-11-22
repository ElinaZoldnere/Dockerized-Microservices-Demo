package lv.javaguru.black.list.dto;

import java.util.List;
import lv.javaguru.black.list.core.api.command.BlackListedPersonCoreCommand;
import lv.javaguru.black.list.core.api.command.BlackListedPersonCoreResult;
import lv.javaguru.black.list.core.api.command.BlackListedPersonCoreResultErrors;
import lv.javaguru.black.list.core.api.command.BlackListedPersonCoreResultSuccess;
import lv.javaguru.black.list.core.api.dto.PersonDTO;
import lv.javaguru.black.list.core.api.dto.ValidationErrorDTO;
import org.springframework.stereotype.Component;

@Component
public class DTOConverter {

    public BlackListedPersonCoreCommand buildCoreCommand(BlackListedPersonCheckRequest request) {
        return new BlackListedPersonCoreCommand(
                new PersonDTO(
                        request.personFirstName(),
                        request.personLastName(),
                        request.personalCode()
                )
        );
    }

    public BlackListedPersonCheckResponse buildResponse(BlackListedPersonCoreResult result) {
        return result instanceof BlackListedPersonCoreResultSuccess
                ? buildSuccessfulResponse(result)
                : buildErrorResponse(result);
    }

    private BlackListedPersonCheckResponseSuccess buildSuccessfulResponse(BlackListedPersonCoreResult result) {
        BlackListedPersonCoreResultSuccess successResult = (BlackListedPersonCoreResultSuccess) result;
        PersonDTO person = successResult.getPerson();
        return new BlackListedPersonCheckResponseSuccess(
                person.personFirstName(),
                person.personLastName(),
                person.personalCode(),
                successResult.isBlackListed());
    }

    private BlackListedPersonCheckResponseErrors buildErrorResponse(BlackListedPersonCoreResult result) {
        BlackListedPersonCoreResultErrors errorResult = (BlackListedPersonCoreResultErrors) result;
        List<ValidationError> errors = transformErrors(errorResult.getErrors());
        return new BlackListedPersonCheckResponseErrors(errors);
    }

    private List<ValidationError> transformErrors(List<ValidationErrorDTO> coreErrors) {
        return coreErrors.stream()
                .map(error -> new ValidationError(
                        error.errorCode(),
                        error.description()))
                .toList();
    }

}
