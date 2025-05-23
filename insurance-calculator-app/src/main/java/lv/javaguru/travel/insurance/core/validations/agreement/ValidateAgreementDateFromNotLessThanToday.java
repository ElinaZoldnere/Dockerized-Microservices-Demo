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
class ValidateAgreementDateFromNotLessThanToday extends AgreementFieldValidationImpl {

    private final DateTimeUtil dateTimeUtil;
    private final ValidationErrorFactory validationErrorFactory;

    @Override
    public Optional<ValidationErrorDTO> validateSingle(AgreementDTO agreement) {
        LocalDate agreementDateFrom = agreement.agreementDateFrom();
        LocalDate currentDate = dateTimeUtil.currentDate();

        return (agreementDateFrom != null && agreementDateFrom.isBefore(currentDate))
                ? Optional.of(validationErrorFactory.buildError("ERROR_CODE_11"))
                : Optional.empty();
    }

}
