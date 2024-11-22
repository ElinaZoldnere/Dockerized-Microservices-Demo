package lv.javaguru.travel.insurance.core.services.writers.entity;

import lombok.RequiredArgsConstructor;
import lv.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import lv.javaguru.travel.insurance.core.domain.entities.AgreementEntity;
import lv.javaguru.travel.insurance.core.domain.entities.SelectedRisksEntity;
import lv.javaguru.travel.insurance.core.repositories.entities.SelectedRisksEntityRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class SelectedRisksWriter {

    private final SelectedRisksEntityRepository repository;

    void writeSelectedRisks(AgreementDTO agreement, AgreementEntity agreementEntity) {
        agreement.selectedRisks().forEach(riskIc -> {
            SelectedRisksEntity riskEntity = new SelectedRisksEntity();
            riskEntity.setAgreementEntity(agreementEntity);
            riskEntity.setRiskIc(riskIc);
            repository.save(riskEntity);
        });
    }

}
