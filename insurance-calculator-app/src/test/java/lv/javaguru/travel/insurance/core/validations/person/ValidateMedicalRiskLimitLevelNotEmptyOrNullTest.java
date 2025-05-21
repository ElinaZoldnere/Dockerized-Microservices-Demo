package lv.javaguru.travel.insurance.core.validations.person;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.stream.Stream;
import lv.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import lv.javaguru.travel.insurance.core.api.dto.AgreementDTOTestBuilder;
import lv.javaguru.travel.insurance.core.api.dto.PersonDTO;
import lv.javaguru.travel.insurance.core.api.dto.PersonDTOTestBuilder;
import lv.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import lv.javaguru.travel.insurance.core.validations.ValidationErrorFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class ValidateMedicalRiskLimitLevelNotEmptyOrNullTest {

    @Mock
    private ValidationErrorFactory errorFactoryMock;

    @InjectMocks
    private ValidateMedicalRiskLimitLevelNotEmpty validate;

    @ParameterizedTest(name = "{0}")
    @MethodSource("medicalRiskLimitLevelValue")
    public void validateSingleShouldReturnErrorWhenMedicalRiskLimitLevelIsEnabledAndFieldIsNotValid(
            String testName, String medicalRiskLimitLevel) {
        ReflectionTestUtils.setField(validate, "medicalRiskLimitLevelEnabled", Boolean.TRUE);

        AgreementDTO agreement = AgreementDTOTestBuilder.createAgreement()
                .withSelectedRisk("TRAVEL_MEDICAL")
                .build();
        PersonDTO person = PersonDTOTestBuilder.createPerson()
                .withMedicalRiskLimitLevel(medicalRiskLimitLevel)
                .build();

        when(errorFactoryMock.buildError("ERROR_CODE_8"))
                .thenReturn(new ValidationErrorDTO("ERROR_CODE_8",
                        "Field medicalRiskLimitLevel is empty when medical risk limit level enabled!"));

        Optional<ValidationErrorDTO> result = validate.validateSingle(agreement, person);

        assertThat(result)
                .isPresent()
                .hasValueSatisfying(error -> {
                    assertThat(error.errorCode()).isEqualTo("ERROR_CODE_8");
                    assertThat(error.description()).isEqualTo(
                            "Field medicalRiskLimitLevel is empty when medical risk limit level enabled!");
                });
    }

    private static Stream<Arguments> medicalRiskLimitLevelValue() {
        return Stream.of(
                Arguments.of("medicalRiskLimitLevel is null", null),
                Arguments.of("medicalRiskLimitLevel is empty", ""),
                Arguments.of("medicalRiskLimitLevel is blank", "     ")
        );
    }

    @Test
    void validateSingleShouldNotReturnErrorWhenMedicalRiskLimitLevelIsDisabled() {
        ReflectionTestUtils.setField(validate, "medicalRiskLimitLevelEnabled", Boolean.FALSE);
        AgreementDTO agreement = AgreementDTOTestBuilder.createAgreement().build();
        PersonDTO person = PersonDTOTestBuilder.createPerson().build();

        Optional<ValidationErrorDTO> result = validate.validateSingle(agreement, person);

        assertThat(result).isEmpty();
        verifyNoInteractions(errorFactoryMock);
    }

}
