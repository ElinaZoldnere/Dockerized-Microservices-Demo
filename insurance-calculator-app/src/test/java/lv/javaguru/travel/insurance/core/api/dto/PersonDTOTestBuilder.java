package lv.javaguru.travel.insurance.core.api.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PersonDTOTestBuilder {
    private String personFirstName;
    private String personLastName;
    private String personalCode;
    private LocalDate personBirthDate;
    private String medicalRiskLimitLevel;
    private BigDecimal travelCost;
    private List<RiskDTO> personRisks = new ArrayList<>();

    public static PersonDTOTestBuilder createPerson() {
        return new PersonDTOTestBuilder();
    }

    public PersonDTO build() {
        return new PersonDTO(
                personFirstName,
                personLastName,
                personalCode,
                personBirthDate,
                medicalRiskLimitLevel,
                travelCost,
                personRisks);
    }

    public PersonDTOTestBuilder withPersonFirstName(String personFirstName) {
        this.personFirstName = personFirstName;
        return this;
    }

    public PersonDTOTestBuilder withPersonLastName(String personLastName) {
        this.personLastName = personLastName;
        return this;
    }

    public PersonDTOTestBuilder withPersonalCode(String personalCode) {
        this.personalCode = personalCode;
        return this;
    }

    public PersonDTOTestBuilder withPersonBirthdate(LocalDate personBirthDate) {
        this.personBirthDate = personBirthDate;
        return this;
    }

    public PersonDTOTestBuilder withMedicalRiskLimitLevel(String medicalRiskLimitLevel) {
        this.medicalRiskLimitLevel = medicalRiskLimitLevel;
        return this;
    }

    public PersonDTOTestBuilder withTravelCost(BigDecimal travelCost) {
        this.travelCost = travelCost;
        return this;
    }

    public PersonDTOTestBuilder withPersonRisk(RiskDTO personRisk) {
        this.personRisks.add(personRisk);
        return this;
    }

    public PersonDTOTestBuilder withPersonRisks(List<RiskDTO> personRisks) {
        this.personRisks = personRisks;
        return this;
    }

}
