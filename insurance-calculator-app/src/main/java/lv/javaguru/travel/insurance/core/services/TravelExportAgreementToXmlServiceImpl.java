package lv.javaguru.travel.insurance.core.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import lv.javaguru.travel.insurance.core.api.command.TravelExportAgreementToXmlCoreCommand;
import lv.javaguru.travel.insurance.core.api.command.TravelExportAgreementToXmlCoreResult;
import lv.javaguru.travel.insurance.core.api.command.TravelGetAgreementCoreCommand;
import lv.javaguru.travel.insurance.core.api.command.TravelGetAgreementCoreResult;
import lv.javaguru.travel.insurance.core.services.writers.ExportedAgreementRegistrar;
import lv.javaguru.travel.insurance.core.util.Placeholder;
import lv.javaguru.travel.insurance.core.validations.ValidationErrorFactory;
import lv.javaguru.travel.insurance.dto.serialize.AgreementSerialDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
class TravelExportAgreementToXmlServiceImpl implements TravelExportAgreementToXmlService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TravelExportAgreementToXmlServiceImpl.class);

    private final TravelGetAgreementService getAgreementService;
    private final ExportedAgreementRegistrar registrar;
    private final ValidationErrorFactory errorFactory;
    /**
     * The {@link XmlMapper} is initialized directly in this class to ensure it is only used for
     * XML-specific operations within this service.
     * This limits the scope of the XmlMapper to this class, ensuring that
     * it does not interfere with Spring's automatic configuration of mappers for other
     * serialization formats, as previous attempts to configure it globally led to conflicts.
     */
    private final XmlMapper xmlMapper;

    TravelExportAgreementToXmlServiceImpl(
            TravelGetAgreementService getAgreementService,
            ExportedAgreementRegistrar registrar,
            ValidationErrorFactory errorFactory
    ) {
        this.getAgreementService = getAgreementService;
        this.registrar = registrar;
        this.errorFactory = errorFactory;

        this.xmlMapper = new XmlMapper();
        this.xmlMapper.registerModule(new JavaTimeModule());
    }

    @Value("${agreement.xml.exporter.job.path}")
    private String exportPath;

    @Override
    public TravelExportAgreementToXmlCoreResult exportAgreement(TravelExportAgreementToXmlCoreCommand command) {
        String uuid = command.getUuid();
        Placeholder uuidPlaceH = new Placeholder("UUID", uuid);

        // For debugging purposes
        String absolutePath = Paths.get(exportPath).toAbsolutePath().toString();
        LOGGER.debug("Resolved absolute path for agreement export: {}", absolutePath);

        LOGGER.info("Agreement uuid {} export job started", uuid);

        try {
            return processExport(uuid, uuidPlaceH);

        } catch (JsonProcessingException e) {
            return handleException("ERROR_CODE_81", uuid, uuidPlaceH, e);
        } catch (IOException e) {
            return handleException("ERROR_CODE_82", uuid, uuidPlaceH, e);
        }
    }

    private TravelExportAgreementToXmlCoreResult processExport(String uuid, Placeholder uuidPlaceH)
            throws IOException {

        AgreementSerialDTO agreement = getAgreementData(uuid);
        String xmlString = convertAgreementToXml(agreement);
        boolean success = writeAgreementXmlToFileIfNotExists(uuid, xmlString);

        if (success) {
            LOGGER.info("Agreement uuid {} export job completed", uuid);
            return new TravelExportAgreementToXmlCoreResult();
        } else {
            LOGGER.warn("File already exists for agreement with UUID {}", uuid);
            return new TravelExportAgreementToXmlCoreResult(List.of(
                    errorFactory.buildError("ERROR_CODE_83", List.of(uuidPlaceH))
            ));
        }
    }

    private TravelExportAgreementToXmlCoreResult handleException(
            String errorCode, String uuid, Placeholder uuidPlaceH, Exception e) {
        LOGGER.warn("Exception during export for agreement with UUID {}", uuid, e);
        return new TravelExportAgreementToXmlCoreResult(List.of(
                errorFactory.buildError(errorCode, List.of(uuidPlaceH))
        ));
    }

    private AgreementSerialDTO getAgreementData(String uuid) {
        TravelGetAgreementCoreCommand command = new TravelGetAgreementCoreCommand(uuid);
        TravelGetAgreementCoreResult agreementResult = getAgreementService.getAgreement(command);
        return agreementResult.getAgreement();
    }

    private String convertAgreementToXml(AgreementSerialDTO agreement) throws JsonProcessingException {
        return xmlMapper.writeValueAsString(agreement);
    }

    private boolean writeAgreementXmlToFileIfNotExists(String uuid, String xmlString) throws IOException {
        String filePath = exportPath + "/agreement_" + uuid + ".xml";

        if (Files.exists(Paths.get(filePath))) {
            return false;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) { //try-with-resources
            writer.write(xmlString);
            registrar.registerExport(uuid, LocalDateTime.now());
            return true;
        }
    }

}
