package lv.javaguru.travel.insurance.core.validations.integration;

import java.math.BigDecimal;
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
class ValidateTravelCostIntegrationTest {

    @Autowired
    private TravelAgreementValidator validator;
    @Autowired
    private DateHelper helper;

    @Test
    void validateShouldReturnErrorWhenTravelCostIsNotValid() {
        PersonDTO person = PersonDTOTestBuilder.createPerson()
                .withPersonFirstName("Jānis")
                .withPersonLastName("Bērziņš")
                .withPersonalCode("123456-12345")
                .withPersonBirthdate(helper.newDate("1990.01.01"))
                .withTravelCost(new BigDecimal("2000000"))
                .build();

        AgreementDTO agreement = AgreementDTOTestBuilder.createAgreement()
                .withDateFrom(helper.newDate("2025.03.10"))
                .withDateTo(helper.newDate("2025.03.11"))
                .withCountry("SPAIN")
                .withSelectedRisks(List.of("TRAVEL_CANCELLATION", "TRAVEL_LOSS_BAGGAGE"))
                .withPerson(person)
                .withPremium(BigDecimal.ZERO)
                .build();

        List<ValidationErrorDTO> result = validator.validate(agreement);

        assertThat(result)
                .hasSize(1)
                .extracting("errorCode", "description")
                .containsExactly(
                        Assertions.tuple("ERROR_CODE_94",
                                "Travel Cost value 2000000 is not supported!"));
    }

    @Test
    void validateShouldReturnErrorWhenTravelCostIsNullAndRiskTypeTravelCancellationSelected() {
        PersonDTO person = PersonDTOTestBuilder.createPerson()
                .withPersonFirstName("Jānis")
                .withPersonLastName("Bērziņš")
                .withPersonalCode("123456-12345")
                .withPersonBirthdate(helper.newDate("1990.01.01"))
                .withTravelCost(null)
                .build();

        AgreementDTO agreement = AgreementDTOTestBuilder.createAgreement()
                .withDateFrom(helper.newDate("2025.03.10"))
                .withDateTo(helper.newDate("2025.03.11"))
                .withCountry("SPAIN")
                .withSelectedRisks(List.of("TRAVEL_CANCELLATION", "TRAVEL_LOSS_BAGGAGE"))
                .withPerson(person)
                .withPremium(BigDecimal.ZERO)
                .build();

        List<ValidationErrorDTO> result = validator.validate(agreement);

        assertThat(result)
                .hasSize(1)
                .extracting("errorCode", "description")
                .containsExactly(
                        Assertions.tuple("ERROR_CODE_10",
                                "Field travelCost is empty when travel cancellation risk selected!"));
    }

    @Test
    void validateShouldNotReturnErrorWhenTravelCostIsNullAndRiskTypeTravelCancellationNotSelected() {
        PersonDTO person = PersonDTOTestBuilder.createPerson()
                .withPersonFirstName("Jānis")
                .withPersonLastName("Bērziņš")
                .withPersonalCode("123456-12345")
                .withPersonBirthdate(helper.newDate("1990.01.01"))
                .withTravelCost(null)
                .build();

        AgreementDTO agreement = AgreementDTOTestBuilder.createAgreement()
                .withDateFrom(helper.newDate("2025.03.10"))
                .withDateTo(helper.newDate("2025.03.11"))
                .withCountry("SPAIN")
                .withSelectedRisks(List.of("TRAVEL_LOSS_BAGGAGE"))
                .withPerson(person)
                .withPremium(BigDecimal.ZERO)
                .build();

        List<ValidationErrorDTO> result = validator.validate(agreement);

        assertThat(result).isEmpty();
    }

}
