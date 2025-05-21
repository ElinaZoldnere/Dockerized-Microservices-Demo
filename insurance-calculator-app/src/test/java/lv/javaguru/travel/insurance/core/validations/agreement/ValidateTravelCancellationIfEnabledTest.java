package lv.javaguru.travel.insurance.core.validations.agreement;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.Optional;
import lv.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import lv.javaguru.travel.insurance.core.api.dto.AgreementDTOTestBuilder;
import lv.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import lv.javaguru.travel.insurance.core.validations.ValidationErrorFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class ValidateTravelCancellationIfEnabledTest {

    @Mock
    private ValidationErrorFactory errorFactoryMock;

    @InjectMocks
    private ValidateTravelCancellationIfEnabled validate;

    @Test
    void validateSingleShouldReturnErrorWhenTravelCancellationSelectedButFeatureDisabled() {
        ReflectionTestUtils.setField(validate, "isTripCancellationEnabled", Boolean.FALSE);
        AgreementDTO agreement = AgreementDTOTestBuilder.createAgreement()
                .withSelectedRisk("TRAVEL_CANCELLATION")
                .build();

        when(errorFactoryMock.buildError("ERROR_CODE_61"))
                .thenReturn(new ValidationErrorDTO("ERROR_CODE_61",
                        "Travel cancellation risk currently disabled!"));

        Optional<ValidationErrorDTO> result = validate.validateSingle(agreement);

        assertThat(result)
                .isPresent()
                .hasValueSatisfying(e -> {
                    assertThat(e.errorCode()).isEqualTo("ERROR_CODE_61");
                    assertThat(e.description()).isEqualTo("Travel cancellation risk currently disabled!");
                });
    }

    @Test
    void validateSingleShouldNotReturnErrorWhenTravelCancellationSelectedAndFeatureEnabled() {
        ReflectionTestUtils.setField(validate, "isTripCancellationEnabled", Boolean.TRUE);
        AgreementDTO agreement = AgreementDTOTestBuilder.createAgreement()
                .withSelectedRisk("TRAVEL_CANCELLATION")
                .build();

        Optional<ValidationErrorDTO> result = validate.validateSingle(agreement);

        assertThat(result).isNotPresent();
        verifyNoInteractions(errorFactoryMock);
    }

}
