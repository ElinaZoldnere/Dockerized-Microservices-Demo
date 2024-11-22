package lv.javaguru.travel.insurance.core.api.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AgreementDTOTestBuilder {

    private LocalDate agreementDateFrom;
    private LocalDate agreementDateTo;
    private String country;
    private List<String> selectedRisks = new ArrayList<>();
    private List<PersonDTO> persons = new ArrayList<>();
    private BigDecimal agreementPremium;
    private String uuid;

    public static AgreementDTOTestBuilder createAgreement() {
        return new AgreementDTOTestBuilder();
    }

    public AgreementDTO build() {
        return new AgreementDTO(
                agreementDateFrom,
                agreementDateTo,
                country,
                selectedRisks,
                persons,
                agreementPremium,
                uuid);
    }

    public AgreementDTOTestBuilder withDateFrom(LocalDate agreementDateFrom) {
        this.agreementDateFrom = agreementDateFrom;
        return this;
    }

    public AgreementDTOTestBuilder withDateTo(LocalDate agreementDateTo) {
        this.agreementDateTo = agreementDateTo;
        return this;
    }

    public AgreementDTOTestBuilder withCountry(String country) {
        this.country = country;
        return this;
    }

    public AgreementDTOTestBuilder withPremium(BigDecimal agreementPremium) {
        this.agreementPremium = agreementPremium;
        return this;
    }

    public AgreementDTOTestBuilder withSelectedRisk(String selectedRisk) {
        this.selectedRisks.add(selectedRisk);
        return this;
    }

    public AgreementDTOTestBuilder withSelectedRisks(List<String> selectedRisks) {
        this.selectedRisks = selectedRisks;
        return this;
    }

    public AgreementDTOTestBuilder withPerson(PersonDTO person) {
        this.persons.add(person);
        return this;
    }

    public AgreementDTOTestBuilder withPersons(List<PersonDTO> persons) {
        this.persons = persons;
        return this;
    }

    public AgreementDTOTestBuilder withUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

}
