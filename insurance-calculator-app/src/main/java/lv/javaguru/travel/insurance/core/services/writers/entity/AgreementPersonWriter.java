package lv.javaguru.travel.insurance.core.services.writers.entity;

import lombok.RequiredArgsConstructor;
import lv.javaguru.travel.insurance.core.api.dto.PersonDTO;
import lv.javaguru.travel.insurance.core.domain.entities.AgreementEntity;
import lv.javaguru.travel.insurance.core.domain.entities.AgreementPersonEntity;
import lv.javaguru.travel.insurance.core.repositories.entities.AgreementPersonEntityRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class AgreementPersonWriter {

    private final AgreementPersonEntityRepository repository;
    private final PersonWriter personWriter;

    AgreementPersonEntity writeAgreementPerson(PersonDTO person, AgreementEntity agreementEntity) {
        AgreementPersonEntity agreementPersonEntity = new AgreementPersonEntity();
        agreementPersonEntity.setAgreementEntity(agreementEntity);
        agreementPersonEntity.setPersonEntity(personWriter.writePersonIfNotExists(person));
        agreementPersonEntity.setMedicalRiskLimitLevel(person.medicalRiskLimitLevel());
        return repository.save(agreementPersonEntity);
    }

}

