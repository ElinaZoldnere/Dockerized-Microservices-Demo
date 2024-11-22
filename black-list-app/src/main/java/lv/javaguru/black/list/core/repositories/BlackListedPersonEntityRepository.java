package lv.javaguru.black.list.core.repositories;

import java.util.Optional;
import lv.javaguru.black.list.core.domain.BlackListedPersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BlackListedPersonEntityRepository extends JpaRepository<BlackListedPersonEntity, Long> {
    @Query("SELECT bpe from BlackListedPersonEntity bpe "
            + "where bpe.firstName = :firstName "
            + "and bpe.lastName = :lastName "
            + "and bpe.personalCode = :personalCode")
    Optional<BlackListedPersonEntity> findBy(
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            @Param("personalCode") String personalCode
    );

}
