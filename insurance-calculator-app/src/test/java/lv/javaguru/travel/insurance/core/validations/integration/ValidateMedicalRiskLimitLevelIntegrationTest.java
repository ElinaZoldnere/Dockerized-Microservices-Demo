package lv.javaguru.travel.insurance.core.validations.integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;
import lv.javaguru.travel.insurance.config.SharedIntegrationTest;
import lv.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import lv.javaguru.travel.insurance.core.api.dto.AgreementDTOTestBuilder;
import lv.javaguru.travel.insurance.core.api.dto.PersonDTO;
import lv.javaguru.travel.insurance.core.api.dto.PersonDTOTestBuilder;
import lv.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import lv.javaguru.travel.insurance.core.util.DateHelper;
import lv.javaguru.travel.insurance.core.validations.TravelAgreementValidator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@SharedIntegrationTest
class ValidateMedicalRiskLimitLevelIntegrationTest {

    @Autowired
    private TravelAgreementValidator validator;
    @Autowired
    private DateHelper helper;

    @Test
    void validateShouldReturnErrorWhenMedicalRiskLimitLevelIsNotValid() {
        PersonDTO person = PersonDTOTestBuilder.createPerson()
                .withPersonFirstName("Jānis")
                .withPersonLastName("Bērziņš")
                .withPersonalCode("123456-12345")
                .withPersonBirthdate(helper.newDate("1990.01.01"))
                .withMedicalRiskLimitLevel("INVALID")
                .build();

        AgreementDTO agreement = AgreementDTOTestBuilder.createAgreement()
                .withDateFrom(helper.newDate("2025.06.10"))
                .withDateTo(helper.newDate("2025.06.11"))
                .withCountry("SPAIN")
                .withSelectedRisks(List.of("TRAVEL_MEDICAL", "TRAVEL_LOSS_BAGGAGE"))
                .withPerson(person)
                .withPremium(BigDecimal.ZERO)
                .build();

        List<ValidationErrorDTO> result = validator.validate(agreement);

        assertThat(result)
                .hasSize(1)
                .extracting("errorCode", "description")
                .containsExactly(
                        Assertions.tuple("ERROR_CODE_93",
                                "Medical Risk Limit Level value INVALID is not supported!"));
    }

    @Test
    void validateShouldReturnErrorMedicalRiskLimitLevelIsNullAndRiskTypeTravelMedicalSelected() {
        PersonDTO person = PersonDTOTestBuilder.createPerson()
                .withPersonFirstName("Jānis")
                .withPersonLastName("Bērziņš")
                .withPersonalCode("123456-12345")
                .withPersonBirthdate(helper.newDate("1990.01.01"))
                .withMedicalRiskLimitLevel(null)
                .build();

        AgreementDTO agreement = AgreementDTOTestBuilder.createAgreement()
                .withDateFrom(helper.newDate("2025.06.10"))
                .withDateTo(helper.newDate("2025.06.11"))
                .withCountry("SPAIN")
                .withSelectedRisks(List.of("TRAVEL_MEDICAL", "TRAVEL_LOSS_BAGGAGE"))
                .withPerson(person)
                .withPremium(BigDecimal.ZERO)
                .build();

        List<ValidationErrorDTO> result = validator.validate(agreement);

        assertThat(result)
                .hasSize(1)
                .extracting("errorCode", "description")
                .containsExactly(
                        Assertions.tuple("ERROR_CODE_8",
                                "Field medicalRiskLimitLevel is empty when medical risk limit level enabled!"));
    }

    @Test
    void validateShouldNotReturnErrorMedicalRiskLimitLevelIsNullAndRiskTypeTravelMedicalNotSelected() {
        PersonDTO person = PersonDTOTestBuilder.createPerson()
                .withPersonFirstName("Jānis")
                .withPersonLastName("Bērziņš")
                .withPersonalCode("123456-12345")
                .withPersonBirthdate(helper.newDate("1990.01.01"))
                .withMedicalRiskLimitLevel(null)
                .build();

        AgreementDTO agreement = AgreementDTOTestBuilder.createAgreement()
                .withDateFrom(helper.newDate("2025.06.10"))
                .withDateTo(helper.newDate("2025.06.11"))
                .withCountry("SPAIN")
                .withSelectedRisks(List.of("TRAVEL_LOSS_BAGGAGE"))
                .withPerson(person)
                .withPremium(BigDecimal.ZERO)
                .build();

        List<ValidationErrorDTO> result = validator.validate(agreement);

        assertThat(result).isEmpty();
    }

}
