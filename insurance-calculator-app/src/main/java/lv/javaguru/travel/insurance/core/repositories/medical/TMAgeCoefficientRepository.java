package lv.javaguru.travel.insurance.core.repositories.medical;

import java.util.Optional;
import lv.javaguru.travel.insurance.core.domain.medical.TMAgeCoefficient;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TMAgeCoefficientRepository extends JpaRepository<TMAgeCoefficient, Long> {
    @Cacheable("tmAgeCoefficientCache")
    @Query("SELECT ac from TMAgeCoefficient ac "
            + "where ac.ageFrom <= :age "
            + "and ac.ageTo >= :age")
    Optional<TMAgeCoefficient> findCoefficient(@Param("age") Integer age);

}
