package lv.javaguru.travel.insurance.core.repositories.cancellation;

import java.math.BigDecimal;
import java.util.Optional;
import lv.javaguru.travel.insurance.core.domain.cancellation.TCTravelCostCoefficient;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TCTravelCostCoefficientRepository extends JpaRepository<TCTravelCostCoefficient, Long> {

    @Cacheable("tcTravelCostCoefficientCache")
    @Query("SELECT tc from TCTravelCostCoefficient tc "
            + "where tc.travelCostFrom <= :travel_cost "
            + "and tc.travelCostTo >= :travel_cost")
    Optional<TCTravelCostCoefficient> findCoefficientByTravelCost(@Param("travel_cost") BigDecimal travelCost);

}
