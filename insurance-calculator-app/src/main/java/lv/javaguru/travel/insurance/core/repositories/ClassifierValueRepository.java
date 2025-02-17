package lv.javaguru.travel.insurance.core.repositories;

import java.util.List;
import java.util.Optional;
import lv.javaguru.travel.insurance.core.domain.ClassifierValue;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ClassifierValueRepository extends JpaRepository<ClassifierValue, Long> {
    @Cacheable("classifierValueCache")
    @Query("SELECT cv from ClassifierValue cv "
            + "left join cv.classifier c "
            + "where c.title = :classifierTitle "
            + "and cv.ic = :ic")
    Optional<ClassifierValue> findByClassifierTitleAndIc(
            @Param("classifierTitle") String classifierTitle,
            @Param("ic") String ic
    );

    @SuppressWarnings("checkstyle:MethodName")
    List<ClassifierValue> findByClassifier_Title(String classifierTitle);

}
