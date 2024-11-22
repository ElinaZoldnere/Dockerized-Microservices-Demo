package lv.javaguru.travel.insurance.core.validations.person;

import java.util.Optional;
import java.util.stream.Stream;
import lv.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import lv.javaguru.travel.insurance.core.api.dto.AgreementDTOTestBuilder;
import lv.javaguru.travel.insurance.core.api.dto.PersonDTO;
import lv.javaguru.travel.insurance.core.api.dto.PersonDTOTestBuilder;
import lv.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import lv.javaguru.travel.insurance.core.validations.ValidationErrorFactory;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ValidatePersonFirstNameFormatTest {

    @Mock
    private ValidationErrorFactory errorMock;

    @InjectMocks
    private ValidatePersonFirstNameFormat validate;

    @ParameterizedTest(name = "{0}")
    @MethodSource("personFirstNameValue")
    public void validateShouldReturnErrorWhenPersonFirstNameIsNotValid(String testName, String personFirstName) {
        AgreementDTO agreement = AgreementDTOTestBuilder.createAgreement().build();
        PersonDTO person = PersonDTOTestBuilder.createPerson()
                .withPersonFirstName(personFirstName)
                .build();

        when(errorMock.buildError("ERROR_CODE_16"))
                .thenReturn(new ValidationErrorDTO("ERROR_CODE_16",
                        "Wrong personFirstName format! Allowed symbols include Latin and Latvian "
                                + "characters, hyphens, and spaces."));

        Optional<ValidationErrorDTO> result = validate.validateSingle(agreement, person);

        assertThat(result)
                .isPresent()
                .hasValueSatisfying(error -> {
                    assertThat(error.errorCode()).isEqualTo("ERROR_CODE_16");
                    assertThat(error.description()).isEqualTo("Wrong personFirstName format! Allowed symbols "
                            + "include Latin and Latvian characters, hyphens, and spaces.");
                });
    }

    private static Stream<Arguments> personFirstNameValue() {
        return Stream.of(
                Arguments.of("personFirstName contains numbers", "12345"),
                Arguments.of("personFirstName contains not allowed letters", "Вася"),
                Arguments.of("personFirstName contains not allowed symbols",
                        "Vasja'; DROP DATABASE my-test-database;--")
        );
    }

}
