package lv.javaguru.travel.insurance.core.repositories.cancellation;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Optional;
import lv.javaguru.travel.insurance.core.domain.cancellation.TCCountrySafetyRatingCoefficient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class TCCountrySafetyRatingCoefficientRepositoryTest {

    @Autowired
    private TCCountrySafetyRatingCoefficientRepository repository;

    @Test
    void injectedRepositoryAreNotNull() {
        assertThat(repository).isNotNull();
    }

    @Test
    void findByCountryIcShouldFindExistingCoefficient() {
        Optional<TCCountrySafetyRatingCoefficient> dayRateOpt = repository.findCoefficientByCountryIc("SPAIN");

        assertThat(dayRateOpt)
                .get()
                .satisfies(d -> assertThat(d.getCoefficient()).isEqualTo(new BigDecimal("8.00")));
    }

    @Test
    void findByCountryIcShouldNotFindCoefficientWhenAgeIsNull() {
        Optional<TCCountrySafetyRatingCoefficient> dayRateOpt = repository.findCoefficientByCountryIc(null);

        assertThat(dayRateOpt).isEmpty();
    }

    @Test
    void findByCountryIcShouldNotFindCoefficientWhenCoefficientDoesNotExist() {
        Optional<TCCountrySafetyRatingCoefficient> dayRateOpt = repository.findCoefficientByCountryIc("INVALID");

        assertThat(dayRateOpt).isEmpty();
    }

}
