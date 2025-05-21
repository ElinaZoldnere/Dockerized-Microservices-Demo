package lv.javaguru.black.list.core.validations;

import static lv.javaguru.black.list.core.api.dto.BlackListedPersonDTOTestBuilder.createBlackListedPerson;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

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

@ExtendWith(MockitoExtension.class)
class ValidatePersonalCodeNotNullOrBlankTest {

    @Mock
    private ValidationErrorFactory errorFactoryMock;

    @InjectMocks
    private ValidatePersonalCodeNotNullOrBlank validate;

    @ParameterizedTest(name = "{0}")
    @MethodSource("personalCodeValue")
    void validateShouldReturnErrorWhenPersonalCodeIsNullOrBlank(String testName, String personalCode) {
        PersonDTO person = createBlackListedPerson()
                .withPersonalCode(personalCode)
                .build();

        when(errorFactoryMock.buildError("ERROR_CODE_3"))
                .thenReturn(new ValidationErrorDTO("ERROR_CODE_3", "Field personalCode is empty!"));

        Optional<ValidationErrorDTO> result = validate.validate(person);

        assertThat(result)
                .hasValueSatisfying(error -> {
                    assertThat(error.errorCode()).isEqualTo("ERROR_CODE_3");
                    assertThat(error.description()).isEqualTo("Field personalCode is empty!");
                });
    }

    private static Stream<Arguments> personalCodeValue() {
        return Stream.of(
                Arguments.of("personalCode is null", null),
                Arguments.of("personalCode is empty", ""),
                Arguments.of("personalCode is blank", "     ")
        );
    }

    @Test
    void validateShouldReturnEmptyOptionalWhenPersonalCodeIsValid() {
        PersonDTO person = createBlackListedPerson()
                .withPersonalCode("123456-12345")
                .build();

        Optional<ValidationErrorDTO> result = validate.validate(person);

        assertThat(result).isEmpty();
    }

}
