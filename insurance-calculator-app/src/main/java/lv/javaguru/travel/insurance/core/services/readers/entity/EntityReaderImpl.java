package lv.javaguru.travel.insurance.core.services.readers.entity;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lv.javaguru.travel.insurance.core.domain.entities.AgreementEntity;
import lv.javaguru.travel.insurance.core.domain.entities.AgreementPersonEntity;
import lv.javaguru.travel.insurance.core.repositories.entities.AgreementEntityRepository;
import lv.javaguru.travel.insurance.core.repositories.entities.AgreementPersonEntityRepository;
import lv.javaguru.travel.insurance.dto.serialize.AgreementSerialDTO;
import lv.javaguru.travel.insurance.dto.serialize.PersonSerialDTO;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class EntityReaderImpl implements EntityReader {

    private final AgreementEntityRepository agreementRepository;
    private final AgreementPersonEntityRepository agreementPersonRepository;
    private final SelectedRisksReader selectedRisksReader;
    private final PersonDTOReader personReader;

    @Override
    public AgreementSerialDTO readEntitiesByUuid(String uuid) {
        AgreementEntity agreement = agreementRepository.findByUuid(uuid).get();

        List<String> selectedRisks = selectedRisksReader.readSelectedRisks(agreement);

        List<AgreementPersonEntity> agreementPersons = agreementPersonRepository.findByAgreementEntity(agreement);

        List<PersonSerialDTO> persons = agreementPersons.stream()
                .map(personReader::readPersonDTO)
                .toList();

        return AgreementSerialDTO.builder()
                .agreementDateFrom(agreement.getDateFrom())
                .agreementDateTo(agreement.getDateTo())
                .country(agreement.getCountry())
                .selectedRisks(selectedRisks)
                .persons(persons)
                .agreementPremium(agreement.getPremium())
                .uuid(agreement.getUuid())
                .build();
    }

}
