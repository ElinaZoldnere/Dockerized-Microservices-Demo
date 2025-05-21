package lv.javaguru.travel.insurance.core.repositories.cancellation;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Optional;
import lv.javaguru.travel.insurance.core.domain.cancellation.TCAgeCoefficient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class TCAgeCoefficientRepositoryTest {

    @Autowired
    private TCAgeCoefficientRepository repository;

    @Test
    void injectedRepositoryAreNotNull() {
        assertThat(repository).isNotNull();
    }

    @Test
    void findAgeCoefficientShouldFindExistingCoefficient() {
        Optional<TCAgeCoefficient> ageCoefficientOpt = repository.findCoefficientByAge(30);

        assertThat(ageCoefficientOpt)
                .get()
                .satisfies(a -> assertThat(a.getCoefficient()).isEqualTo(new BigDecimal("20.00")));
    }

    @Test
    void findAgeCoefficientShouldNotFindCoefficientWhenAgeIsNull() {
        Optional<TCAgeCoefficient> ageCoefficientOpt = repository.findCoefficientByAge(null);

        assertThat(ageCoefficientOpt).isEmpty();
    }

    @Test
    void findAgeCoefficientShouldNotFindCoefficientWhenCoefficientDoesNotExist() {
        Optional<TCAgeCoefficient> ageCoefficientOpt =
                repository.findCoefficientByAge(160);

        assertThat(ageCoefficientOpt).isEmpty();
    }

}
