package lv.javaguru.doc.generator.core.messagebroker.proposalgenerator;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lv.javaguru.doc.generator.core.api.dto.AckMessageDTO;
import lv.javaguru.doc.generator.core.api.dto.AgreementDTO;
import lv.javaguru.doc.generator.core.messagebroker.proposalack.ProposalGenerationAckSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class ProposalGeneratorImpl implements ProposalGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProposalGeneratorImpl.class);

    private final MessageToAgreementDTOConverter converter;
    private final PDFCreator pdfCreator;
    private final ProposalGenerationAckSender ackSender;

    @Value("${proposals.directory.path}")
    String directoryPath;

    @Override
    public void generateProposal(String message) throws IOException {

        AgreementDTO agreementDTO = converter.convert(message);
        Path path = Paths.get(directoryPath, fileName(agreementDTO));

        try {
            pdfCreator.createPdf(agreementDTO, path);
        } catch (IOException e) {
            LOGGER.error("Error writing to file at path: {}", path, e);
            throw e;
        }
        ackSender.sendProposalGenerationAck(new AckMessageDTO(agreementDTO.uuid(), directoryPath, LocalDateTime.now()));
    }

    private String fileName(AgreementDTO agreement) {
        return "agreement-proposal-" + agreement.uuid() + ".pdf";
    }

}
