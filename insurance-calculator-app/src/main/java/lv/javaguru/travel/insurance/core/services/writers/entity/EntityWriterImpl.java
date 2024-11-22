package lv.javaguru.travel.insurance.core.services.writers.entity;

import lombok.RequiredArgsConstructor;
import lv.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import lv.javaguru.travel.insurance.core.domain.entities.AgreementEntity;
import lv.javaguru.travel.insurance.core.domain.entities.AgreementPersonEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class EntityWriterImpl implements EntityWriter {

    private final AgreementWriter agreementWriter;
    private final SelectedRisksWriter risksWriter;
    private final AgreementPersonWriter agreementPersonWriter;
    private final AgreementPersonRisksWriter agreementPersonRisksWriter;

    @Override
    public void writeEntities(AgreementDTO agreement) {
        AgreementEntity agreementEntity = agreementWriter.writeAgreement(agreement);
        agreement.persons()
                .forEach(person -> {
                    AgreementPersonEntity agreementPersonEntity =
                            agreementPersonWriter.writeAgreementPerson(person, agreementEntity);
                    agreementPersonRisksWriter.writeAgreementPersonRisks(person, agreementPersonEntity);
                });
        risksWriter.writeSelectedRisks(agreement, agreementEntity);
    }

}
