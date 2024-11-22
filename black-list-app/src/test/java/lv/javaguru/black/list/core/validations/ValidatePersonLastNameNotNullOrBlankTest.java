package lv.javaguru.black.list.core.validations;

import java.util.Optional;
import java.util.stream.Stream;
import lv.javaguru.black.list.core.api.dto.PersonDTO;
import lv.javaguru.black.list.core.api.dto.ValidationErrorDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static lv.javaguru.black.list.core.api.dto.BlackListedPersonDTOTestBuilder.createBlackListedPerson;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ValidatePersonLastNameNotNullOrBlankTest {

    @Mock
    private ValidationErrorFactory errorFactoryMock;

    @InjectMocks
    private ValidatePersonLastNameNotNullOrBlank validate;

    @ParameterizedTest(name = "{0}")
    @MethodSource("personLastName")
    void validateShouldReturnErrorWhenPersonalCodeIsNullOrBlank(String testName, String personLastName) {
        PersonDTO person = createBlackListedPerson()
                .withPersonLastName(personLastName)
                .build();

        when(errorFactoryMock.buildError("ERROR_CODE_2"))
                .thenReturn(new ValidationErrorDTO("ERROR_CODE_2",
                        "Field personLastName is empty!"));

        Optional<ValidationErrorDTO> result = validate.validate(person);

        assertThat(result)
                .hasValueSatisfying(error -> {
                    assertThat(error.errorCode()).isEqualTo("ERROR_CODE_2");
                    assertThat(error.description()).isEqualTo("Field personLastName is empty!");
                });
    }

    private static Stream<Arguments> personLastName() {
        return Stream.of(
                Arguments.of("personLastName is null", null),
                Arguments.of("personLastName is empty", ""),
                Arguments.of("personLastName is blank", "     ")
        );
    }

    @Test
    void validateShouldReturnEmptyOptionalWhenPersonLastNameIsValid() {
        PersonDTO person = createBlackListedPerson()
                .withPersonLastName("Bērziņš")
                .build();

        Optional<ValidationErrorDTO> result = validate.validate(person);

        assertThat(result).isEmpty();
    }

}
