package lv.javaguru.travel.insurance.core.validations;

import java.util.List;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lv.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import lv.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class TravelAgreementValidatorImpl implements TravelAgreementValidator {

    private final TravelAgreementAllFieldValidator agreementAllFieldValidator;
    private final TravelPersonAllFieldValidator personAllFieldValidator;

    @Override
    public List<ValidationErrorDTO> validate(AgreementDTO agreement) {
        List<ValidationErrorDTO> agreementErrors = agreementAllFieldValidator.collectAgreementErrors(agreement);
        List<ValidationErrorDTO> personErrors = personAllFieldValidator.collectPersonErrors(agreement);

        return concatenateLists(agreementErrors, personErrors);
    }

    private List<ValidationErrorDTO> concatenateLists(List<ValidationErrorDTO> errorsListOne,
                                                      List<ValidationErrorDTO> errorListTwo) {
        return Stream.concat(errorsListOne.stream(), errorListTwo.stream())
                .toList();
    }

}
