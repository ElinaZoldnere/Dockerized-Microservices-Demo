package lv.javaguru.travel.insurance.core.repositories.cancellation;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Optional;
import lv.javaguru.travel.insurance.core.domain.cancellation.TCTravelCostCoefficient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class TravelCostCoefficientRepositoryTest {

    @Autowired
    private TCTravelCostCoefficientRepository repository;

    @Test
    void injectedRepositoryAreNotNull() {
        assertThat(repository).isNotNull();
    }

    @Test
    void findByTravelCostShouldFindExistingCoefficient() {
        Optional<TCTravelCostCoefficient> travelCostCoefficientOpt =
                repository.findCoefficientByTravelCost(new BigDecimal("6000"));

        assertThat(travelCostCoefficientOpt)
                .get()
                .satisfies(t -> assertThat(t.getCoefficient()).isEqualTo(new BigDecimal("30.00")));
    }

    @Test
    void findCoefficientByTravelCostShouldNotFindCoefficientWhenTravelCostIsNull() {
        Optional<TCTravelCostCoefficient> travelCostCoefficientOpt = repository.findCoefficientByTravelCost(null);

        assertThat(travelCostCoefficientOpt).isEmpty();
    }

    @Test
    void findCoefficientByTravelCostShouldNotFindCoefficientWhenCoefficientDoesNotExist() {
        Optional<TCTravelCostCoefficient> travelCostCoefficientOpt =
                repository.findCoefficientByTravelCost(new BigDecimal("10000000"));

        assertThat(travelCostCoefficientOpt).isEmpty();
    }

}
