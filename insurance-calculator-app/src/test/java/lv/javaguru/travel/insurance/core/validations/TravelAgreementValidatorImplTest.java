package lv.javaguru.travel.insurance.core.validations;

import java.util.List;
import lv.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import lv.javaguru.travel.insurance.core.api.dto.AgreementDTOTestBuilder;
import lv.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import lv.javaguru.travel.insurance.core.api.dto.ValidationErrorDTOTestBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TravelAgreementValidatorImplTest {

    @Mock
    private TravelAgreementAllFieldValidator agreementAllFieldValidator;
    @Mock
    private TravelPersonAllFieldValidator personAllFieldValidator;

    @InjectMocks
    private TravelAgreementValidatorImpl validator;

    @Test
    void validateShouldSumUpErrorsCorrectly() {
        AgreementDTO agreement = AgreementDTOTestBuilder.createAgreement().build();
        ValidationErrorDTO validationError1 = ValidationErrorDTOTestBuilder.createValidationError().build();
        ValidationErrorDTO validationError2 = ValidationErrorDTOTestBuilder.createValidationError().build();
        ValidationErrorDTO validationError3 = ValidationErrorDTOTestBuilder.createValidationError().build();

        when(agreementAllFieldValidator.collectAgreementErrors(agreement))
                .thenReturn(List.of(validationError1, validationError2));
        when(personAllFieldValidator.collectPersonErrors(agreement)).thenReturn(List.of(validationError3));

        List<ValidationErrorDTO> result = validator.validate(agreement);

        assertThat(result).hasSize(3);
    }

}
