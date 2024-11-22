package lv.javaguru.travel.insurance.core.validations.agreement;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lv.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import lv.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import lv.javaguru.travel.insurance.core.validations.ValidationErrorFactory;
import lv.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;
import lv.javaguru.travel.insurance.dto.v2.DtoV2Converter;
import lv.javaguru.travel.insurance.dto.v2.TravelCalculatePremiumRequestV2;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class ValidatePersonsNotEmpty extends AgreementFieldValidationImpl {

    private final ValidationErrorFactory validationErrorFactory;

    /**
     * <p>No null-check needed, because AgreementDTO 'persons' can never be null. Even if the field 'persons' is null in
     * the {@link TravelCalculatePremiumRequestV2}, it is converted to an empty list by
     * {@link DtoV2Converter#buildCoreCommand(TravelCalculatePremiumRequestV2)}.</p>
     * <p>
     * {@link TravelCalculatePremiumRequestV1} has no field 'persons'.
     */

    @Override
    public Optional<ValidationErrorDTO> validateSingle(AgreementDTO agreement) {
        return agreement.persons().isEmpty()
                ? Optional.of(validationErrorFactory.buildError("ERROR_CODE_18"))
                : Optional.empty();
    }

}
