package lv.javaguru.travel.insurance.core.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import lv.javaguru.travel.insurance.core.api.command.TravelGetAgreementCoreCommand;
import lv.javaguru.travel.insurance.core.api.command.TravelGetAgreementCoreResult;
import lv.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import lv.javaguru.travel.insurance.core.api.dto.ValidationErrorDTOTestBuilder;
import lv.javaguru.travel.insurance.core.services.readers.entity.EntityReader;
import lv.javaguru.travel.insurance.core.validations.TravelAgreementUuidValidator;
import lv.javaguru.travel.insurance.dto.serialize.AgreementSerialDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TravelGetAgreementServiceImplTest {

    @Mock
    private TravelAgreementUuidValidator uuidValidatorMock;
    @Mock
    private EntityReader readerMock;

    @InjectMocks
    private TravelGetAgreementServiceImpl service;

    @Test
    void getAgreementShouldCorrectlyReturnExistingAgreement() {
        TravelGetAgreementCoreCommand command = new TravelGetAgreementCoreCommand("UUID");
        String uuid = command.getUuid();

        when(uuidValidatorMock.validate(command.getUuid())).thenReturn(Collections.emptyList());
        when(readerMock.readEntitiesByUuid(uuid)).thenReturn(mock(AgreementSerialDTO.class));

        TravelGetAgreementCoreResult result = service.getAgreement(command);

        assertThat(result).isNotNull();
        assertThat(result.getErrors()).isNull();
        assertThat(result.getAgreement()).isNotNull();
    }

    @Test
    void getAgreementShouldReturnErrorWhenAgreementDoesNotExists() {
        TravelGetAgreementCoreCommand command = new TravelGetAgreementCoreCommand("NOT-EXISTING-UUID");
        ValidationErrorDTO validationError = ValidationErrorDTOTestBuilder.createValidationError().build();

        when(uuidValidatorMock.validate(command.getUuid())).thenReturn(List.of(validationError));

        TravelGetAgreementCoreResult result = service.getAgreement(command);

        assertThat(result).isNotNull();
        assertThat(result.getErrors()).isNotNull();
        assertThat(result.getAgreement()).isNull();
        verifyNoInteractions(readerMock);
    }

}
