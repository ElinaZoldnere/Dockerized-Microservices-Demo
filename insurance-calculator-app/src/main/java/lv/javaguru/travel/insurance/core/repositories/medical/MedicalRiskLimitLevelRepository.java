package lv.javaguru.travel.insurance.core.repositories.medical;

import java.util.Optional;
import lv.javaguru.travel.insurance.core.domain.medical.MedicalRiskLimitLevel;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicalRiskLimitLevelRepository extends JpaRepository<MedicalRiskLimitLevel, Long> {

    @Cacheable("medicalRiskLimitLevelCache")
    Optional<MedicalRiskLimitLevel> findByMedicalRiskLimitLevelIc(String medicalRiskLimitLevelIc);

}
