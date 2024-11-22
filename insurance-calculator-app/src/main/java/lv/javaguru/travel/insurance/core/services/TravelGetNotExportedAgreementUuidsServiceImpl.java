package lv.javaguru.travel.insurance.core.services;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lv.javaguru.travel.insurance.core.api.command.TravelGetNotExportedAgreementUuidsCoreCommand;
import lv.javaguru.travel.insurance.core.api.command.TravelGetNotExportedAgreementUuidsCoreResult;
import lv.javaguru.travel.insurance.core.repositories.entities.AgreementEntityRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class TravelGetNotExportedAgreementUuidsServiceImpl implements TravelGetNotExportedAgreementUuidsService {

    private final AgreementEntityRepository agreementRepository;

    public TravelGetNotExportedAgreementUuidsCoreResult getUuids(
            TravelGetNotExportedAgreementUuidsCoreCommand command) {
        List<String> notExportedUuids = agreementRepository.findNotExportedUuids();
        return new TravelGetNotExportedAgreementUuidsCoreResult(null, notExportedUuids);
    }

}
