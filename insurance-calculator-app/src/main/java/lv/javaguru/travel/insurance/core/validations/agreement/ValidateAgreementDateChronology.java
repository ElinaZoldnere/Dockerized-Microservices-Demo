package lv.javaguru.travel.insurance.core.validations.agreement;

import java.time.LocalDate;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lv.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import lv.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import lv.javaguru.travel.insurance.core.validations.ValidationErrorFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class ValidateAgreementDateChronology extends AgreementFieldValidationImpl {

    private final ValidationErrorFactory validationErrorFactory;

    @Override
    public Optional<ValidationErrorDTO> validateSingle(AgreementDTO agreement) {
        LocalDate agreementDateFrom = agreement.agreementDateFrom();
        LocalDate agreementDateTo = agreement.agreementDateTo();

        return (agreementDateFrom != null && agreementDateTo != null
                && !agreementDateFrom.isBefore(agreementDateTo))
                ? Optional.of(validationErrorFactory.buildError("ERROR_CODE_13"))
                : Optional.empty();
    }

}
