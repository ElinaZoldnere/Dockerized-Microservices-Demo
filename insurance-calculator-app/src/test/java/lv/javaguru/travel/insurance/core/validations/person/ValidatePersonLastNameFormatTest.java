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
class ValidatePersonLastNameFormatTest {

    @Mock
    private ValidationErrorFactory errorMock;

    @InjectMocks
    private ValidatePersonLastNameFormat validate;

    @ParameterizedTest(name = "{0}")
    @MethodSource("personLastNameValue")
    public void validateShouldReturnErrorWhenPersonLastNameIsNotValid(String testName, String personLastName) {
        AgreementDTO agreement = AgreementDTOTestBuilder.createAgreement().build();
        PersonDTO person = PersonDTOTestBuilder.createPerson()
                .withPersonLastName(personLastName)
                .build();

        when(errorMock.buildError("ERROR_CODE_17"))
                .thenReturn(new ValidationErrorDTO("ERROR_CODE_17",
                        "Wrong personLastName format! Allowed symbols include Latin and Latvian "
                                + "characters, hyphens, and spaces."));

        Optional<ValidationErrorDTO> result = validate.validateSingle(agreement, person);

        assertThat(result)
                .isPresent()
                .hasValueSatisfying(error -> {
                    assertThat(error.errorCode()).isEqualTo("ERROR_CODE_17");
                    assertThat(error.description()).isEqualTo("Wrong personLastName format! Allowed symbols "
                            + "include Latin and Latvian characters, hyphens, and spaces.");
                });
    }

    private static Stream<Arguments> personLastNameValue() {
        return Stream.of(
                Arguments.of("personLastName contains numbers", "12345"),
                Arguments.of("personLastName contains not allowed letters", "Пупкин"),
                Arguments.of("personLastName contains not allowed symbols",
                        "Pupkin'; DROP DATABASE my-test-database;--")
        );
    }

}
