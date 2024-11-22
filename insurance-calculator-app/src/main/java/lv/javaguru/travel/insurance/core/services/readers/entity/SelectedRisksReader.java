package lv.javaguru.travel.insurance.core.services.readers.entity;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lv.javaguru.travel.insurance.core.domain.entities.AgreementEntity;
import lv.javaguru.travel.insurance.core.domain.entities.SelectedRisksEntity;
import lv.javaguru.travel.insurance.core.repositories.entities.SelectedRisksEntityRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class SelectedRisksReader {

    private final SelectedRisksEntityRepository selectedRisksRepository;

    List<String> readSelectedRisks(AgreementEntity agreement) {
        List<SelectedRisksEntity> selectedRisksEntities = selectedRisksRepository.findByAgreementEntity(agreement);
        return selectedRisksEntities.stream()
                .map(SelectedRisksEntity::getRiskIc)
                .toList();
    }

}
