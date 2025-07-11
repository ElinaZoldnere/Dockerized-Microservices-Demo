package lv.javaguru.travel.insurance.core.underwriting.integrations.medical;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import lv.javaguru.travel.insurance.config.TestTimeConfig;
import lv.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import lv.javaguru.travel.insurance.core.api.dto.AgreementDTOTestBuilder;
import lv.javaguru.travel.insurance.core.api.dto.PersonDTO;
import lv.javaguru.travel.insurance.core.api.dto.PersonDTOTestBuilder;
import lv.javaguru.travel.insurance.core.underwriting.TravelCalculatePremiumUnderwriting;
import lv.javaguru.travel.insurance.core.underwriting.TravelPremiumCalculationResult;
import lv.javaguru.travel.insurance.core.util.DateHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(properties = {"age.coefficient.enabled=false", "medical.risk.limit.level.enabled=true"})
@AutoConfigureMockMvc
@Import(TestTimeConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class AgeCoefficientOffMedicalRiskLimitLevelOnIntegrationTest {
    @Autowired
    private TravelCalculatePremiumUnderwriting underwriting;
    @Autowired
    private DateHelper helper;

    @Test
    void calculateAgreementPremiumWhenAgeCoefficientEnabled() {
        PersonDTO person = PersonDTOTestBuilder.createPerson()
                .withPersonFirstName("Jānis")
                .withPersonLastName("Bērziņš")
                .withPersonalCode("123456-12345")
                .withPersonBirthdate(helper.newDate("1990.01.01"))
                .withMedicalRiskLimitLevel("LEVEL_20000")
                .build();

        AgreementDTO agreement = AgreementDTOTestBuilder.createAgreement()
                .withDateFrom(helper.newDate("2025.06.10"))
                .withDateTo(helper.newDate("2025.06.11"))
                .withCountry("SPAIN")
                .withSelectedRisk("TRAVEL_MEDICAL")
                .withPerson(person)
                .build();

        TravelPremiumCalculationResult result = underwriting.calculateAgreementPremium(agreement, person);

        assertThat(result.getAgreementPremium()).isEqualTo(new BigDecimal("3.75"));
    }

}
