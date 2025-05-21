package lv.javaguru.black.list.core.validations;

import static lv.javaguru.black.list.core.api.dto.BlackListedPersonDTOTestBuilder.createBlackListedPerson;
import static lv.javaguru.black.list.core.api.dto.ValidationErrorDTOTestBuilder.createValidationError;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import lv.javaguru.black.list.core.api.dto.PersonDTO;
import lv.javaguru.black.list.core.api.dto.ValidationErrorDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BlackListedPersonFieldValidationImplTest {

    @Mock
    private List<ValidatePersonField> validateFieldMock;

    @InjectMocks
    private BlackListedPersonFieldValidationImpl validator;

    @Test
    void validateShouldReturnOneErrorWhenOneFieldIsNotValid() {
        PersonDTO person = createBlackListedPerson().build();
        ValidationErrorDTO error = createValidationError().build();
        ValidatePersonFirstNameNotNullOrBlank validate1 = mock(ValidatePersonFirstNameNotNullOrBlank.class);

        when(validateFieldMock.stream()).thenReturn(Stream.of(validate1));
        when(validate1.validate(person)).thenReturn(Optional.of(error));

        List<ValidationErrorDTO> errors = validator.validate(person);

        assertThat(errors).hasSize(1);
    }

    @Test
    void validateShouldReturnTwoErrorsWhenTwoFieldsAreNotValid() {
        PersonDTO person = createBlackListedPerson().build();
        ValidationErrorDTO error = createValidationError().build();
        ValidatePersonFirstNameNotNullOrBlank validate1 = mock(ValidatePersonFirstNameNotNullOrBlank.class);
        ValidatePersonLastNameNotNullOrBlank validate2 = mock(ValidatePersonLastNameNotNullOrBlank.class);

        when(validateFieldMock.stream()).thenReturn(Stream.of(validate1, validate2));
        when(validate1.validate(person)).thenReturn(Optional.of(error));
        when(validate2.validate(person)).thenReturn(Optional.of(error));

        List<ValidationErrorDTO> errors = validator.validate(person);

        assertThat(errors).hasSize(2);
    }

    @Test
    void validateShouldReturnNoErrorsWhenAllFieldsAreValid() {
        PersonDTO person = createBlackListedPerson().build();
        ValidatePersonFirstNameNotNullOrBlank validate1 = mock(ValidatePersonFirstNameNotNullOrBlank.class);

        when(validateFieldMock.stream()).thenReturn(Stream.of(validate1));
        when(validate1.validate(person)).thenReturn(Optional.empty());

        List<ValidationErrorDTO> errors = validator.validate(person);

        assertThat(errors).isEmpty();
    }

}
