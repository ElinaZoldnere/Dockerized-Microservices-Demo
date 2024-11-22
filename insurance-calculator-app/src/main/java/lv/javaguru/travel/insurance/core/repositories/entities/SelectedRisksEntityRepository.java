package lv.javaguru.travel.insurance.core.repositories.entities;

import java.util.List;
import lv.javaguru.travel.insurance.core.domain.entities.AgreementEntity;
import lv.javaguru.travel.insurance.core.domain.entities.SelectedRisksEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SelectedRisksEntityRepository extends JpaRepository<SelectedRisksEntity, Long> {

    List<SelectedRisksEntity> findByAgreementEntity(AgreementEntity agreement);

}
