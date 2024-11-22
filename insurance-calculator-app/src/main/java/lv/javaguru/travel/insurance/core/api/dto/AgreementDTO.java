package lv.javaguru.travel.insurance.core.api.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record AgreementDTO(
        LocalDate agreementDateFrom,

        LocalDate agreementDateTo,

        String country,

        List<String> selectedRisks,

        List<PersonDTO> persons,

        BigDecimal agreementPremium,

        String uuid) {

    // Copy constructor that allows to add agreementPremium and updated persons, effectively copying other values
    public AgreementDTO withPremiums(List<PersonDTO> persons, BigDecimal agreementPremium, String uuid) {
        return new AgreementDTO(agreementDateFrom,
                agreementDateTo,
                country,
                selectedRisks,
                persons,
                agreementPremium,
                uuid);
    }
}
