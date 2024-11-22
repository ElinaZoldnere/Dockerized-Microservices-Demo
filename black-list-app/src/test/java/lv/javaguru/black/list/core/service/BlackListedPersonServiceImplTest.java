package lv.javaguru.black.list.core.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lv.javaguru.black.list.core.api.command.BlackListedPersonCoreCommand;
import lv.javaguru.black.list.core.api.command.BlackListedPersonCoreResult;
import lv.javaguru.black.list.core.api.command.BlackListedPersonCoreResultErrors;
import lv.javaguru.black.list.core.api.command.BlackListedPersonCoreResultSuccess;
import lv.javaguru.black.list.core.api.dto.PersonDTO;
import lv.javaguru.black.list.core.api.dto.ValidationErrorDTO;
import lv.javaguru.black.list.core.domain.BlackListedPersonEntity;
import lv.javaguru.black.list.core.repositories.BlackListedPersonEntityRepository;
import lv.javaguru.black.list.core.validations.BlackListedPersonFieldValidation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static lv.javaguru.black.list.core.api.dto.BlackListedPersonDTOTestBuilder.createBlackListedPerson;
import static lv.javaguru.black.list.core.api.dto.ValidationErrorDTOTestBuilder.createValidationError;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BlackListedPersonServiceImplTest {

    @Mock
    private BlackListedPersonFieldValidation validationMock;
    @Mock
    BlackListedPersonEntityRepository repositoryMock;

    @InjectMocks
    private BlackListedPersonServiceImpl service;

    @Test
    void checkPersonGeneratesPersonNotBlackListedResult() {
        PersonDTO person = createBlackListedPerson()
                .withPersonFirstName("Jānis")
                .withPersonLastName("Bērziņš")
                .withPersonalCode("123456-12345")
                .build();
        BlackListedPersonCoreCommand command = new BlackListedPersonCoreCommand(person);

        BlackListedPersonEntity foundPersonEntity = new BlackListedPersonEntity(
                1L, "Jānis", "Bērziņš", "123456-12345"
        );

        when(validationMock.validate(command.person())).thenReturn(Collections.emptyList());
        when(repositoryMock.findBy(
                person.personFirstName(),
                person.personLastName(),
                person.personalCode()
        )).thenReturn(Optional.of(foundPersonEntity));

        BlackListedPersonCoreResult result = service.checkPerson(command);

        assertThat(result)
                .isInstanceOfSatisfying(BlackListedPersonCoreResultSuccess.class, successResult ->
                        assertThat(successResult.getPerson()).isEqualTo(command.person()));
    }

    @Test
    void checkPersonGeneratesErrorResult() {
        PersonDTO person = createBlackListedPerson()
                .withPersonFirstName("")
                .withPersonLastName("Bērziņš")
                .withPersonalCode("123456-12345")
                .build();
        BlackListedPersonCoreCommand command = new BlackListedPersonCoreCommand(person);
        ValidationErrorDTO error = createValidationError()
                .withErrorCode("ERROR_CODE_1")
                .withDescription("Field personFirstName is empty!")
                .build();

        when(validationMock.validate(command.person())).thenReturn(List.of(error));

        BlackListedPersonCoreResult result = service.checkPerson(command);

        assertThat(result).isInstanceOfSatisfying(BlackListedPersonCoreResultErrors.class, errorResult -> {
            assertThat(errorResult.getErrors().size()).isEqualTo(1);
            assertThat(errorResult.getErrors().getFirst()).isEqualTo(error);
        });
    }

}
