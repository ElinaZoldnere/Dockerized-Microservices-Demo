package lv.javaguru.travel.insurance.core.repositories.cancellation;

import java.util.Optional;
import lv.javaguru.travel.insurance.core.domain.cancellation.TCCountrySafetyRatingCoefficient;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TCCountrySafetyRatingCoefficientRepository extends
        JpaRepository<TCCountrySafetyRatingCoefficient, Long> {

    @Cacheable("tcCountrySafetyRatingCoefficientCache")
    Optional<TCCountrySafetyRatingCoefficient> findCoefficientByCountryIc(String countryIc);

}
