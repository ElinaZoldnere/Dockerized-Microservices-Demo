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
class ValidatePersonFirstNameNotNullOrBlankTest {

    @Mock
    private ValidationErrorFactory errorFactoryMock;

    @InjectMocks
    private ValidatePersonFirstNameNotNullOrBlank validate;

    @ParameterizedTest(name = "{0}")
    @MethodSource("personFirstName")
    void validateShouldReturnErrorWhenPersonalCodeIsNullOrBlank(String testName, String personFirstName) {
        PersonDTO person = createBlackListedPerson()
                .withPersonFirstName(personFirstName)
                .build();

        when(errorFactoryMock.buildError("ERROR_CODE_1"))
                .thenReturn(new ValidationErrorDTO("ERROR_CODE_1",
                        "Field personFirstName is empty!"));

        Optional<ValidationErrorDTO> result = validate.validate(person);

        assertThat(result)
                .hasValueSatisfying(error -> {
                    assertThat(error.errorCode()).isEqualTo("ERROR_CODE_1");
                    assertThat(error.description()).isEqualTo("Field personFirstName is empty!");
                });
    }

    private static Stream<Arguments> personFirstName() {
        return Stream.of(
                Arguments.of("personFirstName is null", null),
                Arguments.of("personFirstName is empty", ""),
                Arguments.of("personFirstName is blank", "     ")
        );
    }

    @Test
    void validateShouldReturnEmptyOptionalWhenPersonFirstNameIsValid() {
        PersonDTO person = createBlackListedPerson()
                .withPersonFirstName("Jānis")
                .build();

        Optional<ValidationErrorDTO> result = validate.validate(person);

        assertThat(result).isEmpty();
    }

}
