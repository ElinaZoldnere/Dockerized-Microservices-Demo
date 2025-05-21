package lv.javaguru.travel.insurance.core.validations.person;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.JsonMappingException;
import java.util.Optional;
import lv.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import lv.javaguru.travel.insurance.core.api.dto.AgreementDTOTestBuilder;
import lv.javaguru.travel.insurance.core.api.dto.PersonDTO;
import lv.javaguru.travel.insurance.core.api.dto.PersonDTOTestBuilder;
import lv.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import lv.javaguru.travel.insurance.core.blacklist.BlackListCheckPersonService;
import lv.javaguru.travel.insurance.core.blacklist.dto.BlackListCheckPersonRequest;
import lv.javaguru.travel.insurance.core.validations.ValidationErrorFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ValidatePersonIsNotBlackListedTest {

    @Mock
    private ValidationErrorFactory errorMock;
    @Mock
    private BlackListCheckPersonService serviceMock;

    @InjectMocks
    private ValidatePersonIsNotBlacklisted validate;

    @Test
    public void validateShouldNotCallBlackListServiceWhenPersonFirstNameIsNotValid() {
        AgreementDTO agreement = AgreementDTOTestBuilder.createAgreement().build();
        PersonDTO person = PersonDTOTestBuilder.createPerson()
                .withPersonFirstName(null)
                .withPersonLastName("Bērziņš")
                .withPersonalCode("123456-12345")
                .build();

        Optional<ValidationErrorDTO> result = validate.validateSingle(agreement, person);
        verifyNoInteractions(serviceMock);
    }

    @Test
    public void validateShouldNotCallBlackListServiceWhenPersonLastNameIsNotValid() {
        AgreementDTO agreement = AgreementDTOTestBuilder.createAgreement().build();
        PersonDTO person = PersonDTOTestBuilder.createPerson()
                .withPersonFirstName("Jānis")
                .withPersonLastName("")
                .withPersonalCode("123456-12345")
                .build();

        Optional<ValidationErrorDTO> result = validate.validateSingle(agreement, person);
        verifyNoInteractions(serviceMock);
    }

    @Test
    public void validateShouldNotCallBlackListServiceWhenPersonalCodeIsNotValid() {
        AgreementDTO agreement = AgreementDTOTestBuilder.createAgreement().build();
        PersonDTO person = PersonDTOTestBuilder.createPerson()
                .withPersonFirstName("Jānis")
                .withPersonLastName("Bērziņš")
                .withPersonalCode("     ")
                .build();

        Optional<ValidationErrorDTO> result = validate.validateSingle(agreement, person);
        verifyNoInteractions(serviceMock);
    }

    @Test
    public void validateShouldReturnValidationErrorWhenPersonIsBlackListed() throws Exception {
        AgreementDTO agreement = AgreementDTOTestBuilder.createAgreement().build();
        PersonDTO person = PersonDTOTestBuilder.createPerson()
                .withPersonFirstName("Jānis")
                .withPersonLastName("Bērziņš")
                .withPersonalCode("123456-12345")
                .build();

        when(serviceMock.checkPerson(any(BlackListCheckPersonRequest.class))).thenReturn(true);
        when(errorMock.buildError(eq("ERROR_CODE_31"), anyList()))
                .thenReturn(new ValidationErrorDTO("ERROR_CODE_31",
                        "Person Jānis Bērziņš, 123456-12345 is blacklisted!"));

        Optional<ValidationErrorDTO> result = validate.validateSingle(agreement, person);

        assertThat(result)
                .isPresent()
                .hasValueSatisfying(error -> {
                    assertThat(error.errorCode()).isEqualTo("ERROR_CODE_31");
                    assertThat(error.description()).isEqualTo(
                            "Person Jānis Bērziņš, 123456-12345 is blacklisted!");
                });
    }

    @Test
    public void validateShouldNotReturnValidationErrorWhenPersonIsNotBlackListed() throws Exception {
        AgreementDTO agreement = AgreementDTOTestBuilder.createAgreement().build();
        PersonDTO person = PersonDTOTestBuilder.createPerson()
                .withPersonFirstName("Jānis")
                .withPersonLastName("Bērziņš")
                .withPersonalCode("123456-12345")
                .build();

        when(serviceMock.checkPerson(any(BlackListCheckPersonRequest.class))).thenReturn(false);

        Optional<ValidationErrorDTO> result = validate.validateSingle(agreement, person);
        assertThat(result).isEmpty();
    }

    @Test
    public void validateShouldReturnValidationErrorWhenServiceIsNotAvailable() throws Exception {
        AgreementDTO agreement = AgreementDTOTestBuilder.createAgreement().build();
        PersonDTO person = PersonDTOTestBuilder.createPerson()
                .withPersonFirstName("Jānis")
                .withPersonLastName("Bērziņš")
                .withPersonalCode("123456-12345")
                .build();

        when(serviceMock.checkPerson(any(BlackListCheckPersonRequest.class))).thenThrow(Exception.class);
        when(errorMock.buildError("ERROR_CODE_32"))
                .thenReturn(new ValidationErrorDTO("ERROR_CODE_32",
                        "Blacklist service is not available!"));

        Optional<ValidationErrorDTO> result = validate.validateSingle(agreement, person);

        assertThat(result)
                .isPresent()
                .hasValueSatisfying(error -> {
                    assertThat(error.errorCode()).isEqualTo("ERROR_CODE_32");
                    assertThat(error.description()).isEqualTo("Blacklist service is not available!");
                });
    }

    @Test
    public void validateShouldReturnValidationErrorWhenServiceReturnsUnexpectedResponse() throws Exception {
        AgreementDTO agreement = AgreementDTOTestBuilder.createAgreement().build();
        PersonDTO person = PersonDTOTestBuilder.createPerson()
                .withPersonFirstName("Jānis")
                .withPersonLastName("Bērziņš")
                .withPersonalCode("123456-12345")
                .build();

        when(serviceMock.checkPerson(any(BlackListCheckPersonRequest.class))).thenThrow(JsonMappingException.class);
        when(errorMock.buildError("ERROR_CODE_33"))
                .thenReturn(new ValidationErrorDTO("ERROR_CODE_33",
                        "Unexpected response or validation error from Blacklist service!"));

        Optional<ValidationErrorDTO> result = validate.validateSingle(agreement, person);

        assertThat(result)
                .isPresent()
                .hasValueSatisfying(error -> {
                    assertThat(error.errorCode()).isEqualTo("ERROR_CODE_33");
                    assertThat(error.description()).isEqualTo(
                            "Unexpected response or validation error from Blacklist service!");
                });
    }

}
