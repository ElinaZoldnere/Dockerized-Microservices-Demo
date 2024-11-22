package lv.javaguru.travel.insurance.core.repositories.entities;

import java.util.List;
import lv.javaguru.travel.insurance.core.domain.entities.AgreementPersonEntity;
import lv.javaguru.travel.insurance.core.domain.entities.AgreementPersonRisksEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgreementPersonRisksEntityRepository extends JpaRepository<AgreementPersonRisksEntity, Long> {
    List<AgreementPersonRisksEntity> findByAgreementPersonEntity(AgreementPersonEntity agreementPersonEntity);

}
