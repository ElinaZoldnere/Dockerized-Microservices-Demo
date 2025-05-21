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
class ValidatePersonalCodeFormatTest {

    @Mock
    private ValidationErrorFactory errorMock;

    @InjectMocks
    private ValidatePersonalCodeFormat validate;

    @ParameterizedTest(name = "{0}")
    @MethodSource("personalCodeValue")
    public void validateShouldReturnErrorWhenPersonalCodeIsNotValid(String testName, String personalCode) {
        AgreementDTO agreement = AgreementDTOTestBuilder.createAgreement().build();
        PersonDTO person = PersonDTOTestBuilder.createPerson()
                .withPersonalCode(personalCode)
                .build();

        when(errorMock.buildError("ERROR_CODE_15"))
                .thenReturn(new ValidationErrorDTO("ERROR_CODE_15",
                        "Wrong personalCode format! Use 123456-12345."));

        Optional<ValidationErrorDTO> result = validate.validateSingle(agreement, person);

        assertThat(result)
                .isPresent()
                .hasValueSatisfying(error -> {
                    assertThat(error.errorCode()).isEqualTo("ERROR_CODE_15");
                    assertThat(error.description()).isEqualTo("Wrong personalCode format! Use 123456-12345.");
                });
    }

    private static Stream<Arguments> personalCodeValue() {
        return Stream.of(
                Arguments.of("personalCode is in wrong format", "12345612345"),
                Arguments.of("personalCode is in wrong length", "123456-123456"),
                Arguments.of("personalCode contains letters", "ABCDEF-12345")
        );
    }

}
