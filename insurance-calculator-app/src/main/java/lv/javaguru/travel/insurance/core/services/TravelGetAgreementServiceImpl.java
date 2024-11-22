package lv.javaguru.travel.insurance.core.services;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lv.javaguru.travel.insurance.core.api.command.TravelGetAgreementCoreCommand;
import lv.javaguru.travel.insurance.core.api.command.TravelGetAgreementCoreResult;
import lv.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import lv.javaguru.travel.insurance.core.services.readers.entity.EntityReader;
import lv.javaguru.travel.insurance.core.validations.TravelAgreementUuidValidator;
import lv.javaguru.travel.insurance.dto.serialize.AgreementSerialDTO;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class TravelGetAgreementServiceImpl implements TravelGetAgreementService {

    private final TravelAgreementUuidValidator uuidValidator;
    private final EntityReader reader;

    @Override
    public TravelGetAgreementCoreResult getAgreement(TravelGetAgreementCoreCommand command) {
        List<ValidationErrorDTO> errors = uuidValidator.validate(command.getUuid());
        return errors.isEmpty()
                ? buildAgreementResponse(command.getUuid())
                : buildErrorResponse(errors);
    }

    private TravelGetAgreementCoreResult buildAgreementResponse(String uuid) {
        AgreementSerialDTO agreement = reader.readEntitiesByUuid(uuid);
        TravelGetAgreementCoreResult result = new TravelGetAgreementCoreResult();
        result.setAgreement(agreement);
        return result;
    }

    private TravelGetAgreementCoreResult buildErrorResponse(List<ValidationErrorDTO> errors) {
        return new TravelGetAgreementCoreResult(errors);
    }

}
