package lv.javaguru.travel.insurance.core.repositories;

import java.util.Optional;
import lv.javaguru.travel.insurance.core.domain.Classifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassifierRepository extends JpaRepository<Classifier, Long> {

    @Cacheable("classifierCache")
    Optional<Classifier> findByTitle(String title);

}
