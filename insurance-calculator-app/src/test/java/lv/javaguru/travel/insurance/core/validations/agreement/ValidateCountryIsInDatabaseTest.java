package lv.javaguru.travel.insurance.core.validations.agreement;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.anyList;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.Optional;
import lv.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import lv.javaguru.travel.insurance.core.api.dto.AgreementDTOTestBuilder;
import lv.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import lv.javaguru.travel.insurance.core.domain.ClassifierValue;
import lv.javaguru.travel.insurance.core.repositories.ClassifierValueRepository;
import lv.javaguru.travel.insurance.core.validations.ValidationErrorFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ValidateCountryIsInDatabaseTest {

    @Mock
    private ClassifierValueRepository repositoryMock;
    @Mock
    private ValidationErrorFactory errorFactoryMock;

    @InjectMocks
    private ValidateCountryIsInDatabase validate;

    @Test
    void validateSingleShouldReturnCorrectResponseWhenCountryIsNotSupported() {
        AgreementDTO agreement = AgreementDTOTestBuilder.createAgreement()
                .withCountry("INVALID")
                .build();

        when(repositoryMock.findByClassifierTitleAndIc("COUNTRY", "INVALID"))
                .thenReturn(Optional.empty());

        lenient().when(errorFactoryMock.buildError(eq("ERROR_CODE_92"), anyList()))
                .thenReturn(new ValidationErrorDTO("ERROR_CODE_92", "description"));

        Optional<ValidationErrorDTO> result = validate.validateSingle(agreement);

        assertThat(result)
                .isPresent()
                .hasValueSatisfying(error -> {
                    assertThat(error.errorCode()).isEqualTo("ERROR_CODE_92");
                    assertThat(error.description()).isEqualTo("description");
                });
    }

    @Test
    void validateSingleShouldNotReturnErrorWhenCountryIsInDatabase() {
        AgreementDTO agreement = AgreementDTOTestBuilder.createAgreement()
                .withCountry("SPAIN")
                .build();

        when(repositoryMock.findByClassifierTitleAndIc("COUNTRY", "SPAIN"))
                .thenReturn(Optional.of(new ClassifierValue()));

        Optional<ValidationErrorDTO> result = validate.validateSingle(agreement);

        assertThat(result).isNotPresent();
        verifyNoInteractions(errorFactoryMock);
    }

}
