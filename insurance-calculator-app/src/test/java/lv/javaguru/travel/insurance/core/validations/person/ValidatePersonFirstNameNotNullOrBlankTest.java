package lv.javaguru.travel.insurance.core.validations.person;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

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

@ExtendWith(MockitoExtension.class)
class ValidatePersonFirstNameNotNullOrBlankTest {

    @Mock
    private ValidationErrorFactory errorMock;

    @InjectMocks
    private ValidatePersonFirstNameNotNullOrBlank validate;

    @ParameterizedTest(name = "{0}")
    @MethodSource("firstNameValue")
    public void validateShouldReturnErrorWhenPersonFirstNameIsNullOrBlank(String testName, String firstName) {
        AgreementDTO agreement = AgreementDTOTestBuilder.createAgreement().build();
        PersonDTO person = PersonDTOTestBuilder.createPerson()
                .withPersonFirstName(firstName)
                .build();

        when(errorMock.buildError("ERROR_CODE_1"))
                .thenReturn(new ValidationErrorDTO("ERROR_CODE_1", "Field personFirstName is empty!"));

        Optional<ValidationErrorDTO> result = validate.validateSingle(agreement, person);

        assertThat(result)
                .isPresent()
                .hasValueSatisfying(error -> {
                    assertThat(error.errorCode()).isEqualTo("ERROR_CODE_1");
                    assertThat(error.description()).isEqualTo("Field personFirstName is empty!");
                });
    }

    private static Stream<Arguments> firstNameValue() {
        return Stream.of(
                Arguments.of("personFirstName is null", null),
                Arguments.of("personFirstName is empty", ""),
                Arguments.of("personFirstName is blank", "     ")
        );
    }

}
