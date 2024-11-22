package lv.javaguru.travel.insurance.core.services.readers.entity;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lv.javaguru.travel.insurance.core.domain.entities.AgreementPersonEntity;
import lv.javaguru.travel.insurance.core.domain.entities.AgreementPersonRisksEntity;
import lv.javaguru.travel.insurance.core.domain.entities.PersonEntity;
import lv.javaguru.travel.insurance.core.repositories.entities.AgreementPersonRisksEntityRepository;
import lv.javaguru.travel.insurance.dto.serialize.PersonSerialDTO;
import lv.javaguru.travel.insurance.dto.serialize.RiskSerialDTO;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class PersonDTOReader {

    private final AgreementPersonRisksEntityRepository personRisksRepository;
    private final RiskDTOReader riskDTOReader;

    PersonSerialDTO readPersonDTO(AgreementPersonEntity agreementPerson) {
        PersonEntity person = agreementPerson.getPersonEntity();

        List<AgreementPersonRisksEntity> personRisksEntities =
                personRisksRepository.findByAgreementPersonEntity(agreementPerson);

        List<RiskSerialDTO> personRisks = personRisksEntities.stream()
                .map(riskDTOReader::readRiskDTO)
                .toList();

        return PersonSerialDTO.builder()
                .personFirstName(person.getFirstName())
                .personLastName(person.getLastName())
                .personalCode(person.getPersonalCode())
                .personBirthDate(person.getBirthDate())
                .medicalRiskLimitLevel(agreementPerson.getMedicalRiskLimitLevel())
                .travelCost(agreementPerson.getTravelCost())
                .personRisks(personRisks)
                .build();
    }

}
