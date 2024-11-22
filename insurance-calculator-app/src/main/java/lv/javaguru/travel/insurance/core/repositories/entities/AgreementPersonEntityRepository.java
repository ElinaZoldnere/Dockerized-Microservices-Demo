package lv.javaguru.travel.insurance.core.repositories.entities;

import java.util.List;
import lv.javaguru.travel.insurance.core.domain.entities.AgreementEntity;
import lv.javaguru.travel.insurance.core.domain.entities.AgreementPersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgreementPersonEntityRepository extends JpaRepository<AgreementPersonEntity, Long> {
    List<AgreementPersonEntity> findByAgreementEntity(AgreementEntity agreementEntity);

}
