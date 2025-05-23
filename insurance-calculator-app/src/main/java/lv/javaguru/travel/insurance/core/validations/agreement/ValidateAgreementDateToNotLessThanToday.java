package lv.javaguru.travel.insurance.core.validations.agreement;

import java.time.LocalDate;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lv.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import lv.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import lv.javaguru.travel.insurance.core.util.DateTimeUtil;
import lv.javaguru.travel.insurance.core.validations.ValidationErrorFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class ValidateAgreementDateToNotLessThanToday extends AgreementFieldValidationImpl {

    private final DateTimeUtil dateTimeUtil;
    private final ValidationErrorFactory validationErrorFactory;

    @Override
    public Optional<ValidationErrorDTO> validateSingle(AgreementDTO agreement) {
        LocalDate agreementDateTo = agreement.agreementDateTo();

        return (agreementDateTo != null && agreementDateTo.isBefore(dateTimeUtil.currentDate()))
                ? Optional.of(validationErrorFactory.buildError("ERROR_CODE_12"))
                : Optional.empty();
    }

}
