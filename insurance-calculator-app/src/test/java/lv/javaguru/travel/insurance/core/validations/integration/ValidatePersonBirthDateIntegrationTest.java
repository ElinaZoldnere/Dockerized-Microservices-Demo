package lv.javaguru.travel.insurance.core.validations.integration;

import java.util.List;
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
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
class ValidatePersonBirthDateIntegrationTest {

    @Autowired
    private TravelAgreementValidator validator;
    @Autowired
    private DateHelper helper;

    @Test
    void validateShouldReturnErrorWhenPersonBirthDateIsNull() {
        PersonDTO person = PersonDTOTestBuilder.createPerson()
                .withPersonFirstName("Jānis")
                .withPersonLastName("Bērziņš")
                .withPersonalCode("123456-12345")
                .withPersonBirthdate(null)
                .withMedicalRiskLimitLevel("LEVEL_10000")
                .build();

        AgreementDTO agreement = AgreementDTOTestBuilder.createAgreement()
                .withDateFrom(helper.newDate("2025.03.10"))
                .withDateTo(helper.newDate("2025.03.11"))
                .withCountry("SPAIN")
                .withSelectedRisk("TRAVEL_MEDICAL")
                .withPerson(person)
                .build();

        List<ValidationErrorDTO> result = validator.validate(agreement);

        assertThat(result)
                .hasSize(1)
                .extracting("errorCode", "description")
                .containsExactly(
                        Assertions.tuple("ERROR_CODE_7", "Field personBirthDate is empty!"));
    }

    @Test
    void validateShouldReturnErrorWhenPersonBirthDateAfterCurrentDate() {
        PersonDTO person = PersonDTOTestBuilder.createPerson()
                .withPersonFirstName("Jānis")
                .withPersonLastName("Bērziņš")
                .withPersonalCode("123456-12345")
                .withPersonBirthdate(helper.newDate("2035.01.01"))
                .withMedicalRiskLimitLevel("LEVEL_10000")
                .build();

        AgreementDTO agreement = AgreementDTOTestBuilder.createAgreement()
                .withDateFrom(helper.newDate("2025.03.10"))
                .withDateTo(helper.newDate("2025.03.11"))
                .withCountry("SPAIN")
                .withSelectedRisk("TRAVEL_MEDICAL")
                .withPerson(person)
                .build();

        List<ValidationErrorDTO> result = validator.validate(agreement);

        assertThat(result)
                .hasSize(1)
                .extracting("errorCode", "description")
                .containsExactly(
                        Assertions.tuple("ERROR_CODE_14", "PersonBirthDate is not a valid date!"));
    }

    @Test
    void validateShouldReturnErrorWhenPersonBirthDateLessThanMinimalDate() {
        PersonDTO person = PersonDTOTestBuilder.createPerson()
                .withPersonFirstName("Jānis")
                .withPersonLastName("Bērziņš")
                .withPersonalCode("123456-12345")
                .withPersonBirthdate(helper.newDate("1800.01.01"))
                .withMedicalRiskLimitLevel("LEVEL_10000")
                .build();

        AgreementDTO agreement = AgreementDTOTestBuilder.createAgreement()
                .withDateFrom(helper.newDate("2025.03.10"))
                .withDateTo(helper.newDate("2025.03.11"))
                .withCountry("SPAIN")
                .withSelectedRisk("TRAVEL_MEDICAL")
                .withPerson(person)
                .build();

        List<ValidationErrorDTO> result = validator.validate(agreement);

        assertThat(result)
                .hasSize(1)
                .extracting("errorCode", "description")
                .containsExactly(
                        Assertions.tuple("ERROR_CODE_14", "PersonBirthDate is not a valid date!"));
    }

}