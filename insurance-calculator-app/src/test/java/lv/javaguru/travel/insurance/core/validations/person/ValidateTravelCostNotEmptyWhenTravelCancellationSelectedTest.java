package lv.javaguru.travel.insurance.core.validations.person;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.Optional;
import lv.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import lv.javaguru.travel.insurance.core.api.dto.AgreementDTOTestBuilder;
import lv.javaguru.travel.insurance.core.api.dto.PersonDTO;
import lv.javaguru.travel.insurance.core.api.dto.PersonDTOTestBuilder;
import lv.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import lv.javaguru.travel.insurance.core.validations.ValidationErrorFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ValidateTravelCostNotEmptyWhenTravelCancellationSelectedTest {

    @Mock
    private ValidationErrorFactory errorFactoryMock;

    @InjectMocks
    private ValidateTravelCostNotEmptyWhenTravelCancellationSelected validate;

    @Test
    void validateSingleShouldReturnErrorWhenTravelCostNullAndTravelCancellationSelected() {
        AgreementDTO agreement = AgreementDTOTestBuilder.createAgreement()
                .withSelectedRisk("TRAVEL_CANCELLATION")
                .build();
        PersonDTO person = PersonDTOTestBuilder.createPerson()
                .withTravelCost(null)
                .build();

        when(errorFactoryMock.buildError("ERROR_CODE_10"))
                .thenReturn(new ValidationErrorDTO("ERROR_CODE_10",
                        "Field travelCost is empty when travel cancellation risk selected!"));

        Optional<ValidationErrorDTO> result = validate.validateSingle(agreement, person);

        assertThat(result)
                .isPresent()
                .get()
                .extracting("errorCode", "description")
                .containsExactly("ERROR_CODE_10",
                        "Field travelCost is empty when travel cancellation risk selected!");
    }

    @Test
    void validateSingleShouldNotReturnErrorWhenTravelCancellationNotSelected() {
        AgreementDTO agreement = AgreementDTOTestBuilder.createAgreement()
                .withSelectedRisk("TRAVEL_MEDICAL")
                .build();
        PersonDTO person = PersonDTOTestBuilder.createPerson()
                .withTravelCost(null)
                .build();

        Optional<ValidationErrorDTO> result = validate.validateSingle(agreement, person);

        assertThat(result).isNotPresent();
        verifyNoInteractions(errorFactoryMock);
    }

}
