package lv.javaguru.doc.generator.core.api.dto;

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

}
