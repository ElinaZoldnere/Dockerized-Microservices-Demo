package lv.javaguru.travel.insurance.core.services.writers;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lv.javaguru.travel.insurance.core.domain.entities.ExportedAgreementUuidEntity;
import lv.javaguru.travel.insurance.core.repositories.entities.ExportedAgreementEntityUuidRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(access = lombok.AccessLevel.PACKAGE)
public class ExportedAgreementRegistrar {

    private final ExportedAgreementEntityUuidRepository repository;

    public void registerExport(String uuid, LocalDateTime exportedAt) {
        ExportedAgreementUuidEntity entity = new ExportedAgreementUuidEntity();
        entity.setUuid(uuid);
        entity.setExportedAt(exportedAt);
        repository.save(entity);
    }

}
