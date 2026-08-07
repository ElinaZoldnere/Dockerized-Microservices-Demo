package lv.javaguru.doc.generator.core.messagebroker.proposalgenerator;

import lombok.RequiredArgsConstructor;
import lv.javaguru.doc.generator.core.api.dto.AgreementDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

@Component
@RequiredArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class MessageToAgreementDTOConverter {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageToAgreementDTOConverter.class);

    private final ObjectMapper mapper;

    AgreementDTO convert(String message) {
        try {
            return mapper.readValue(message, AgreementDTO.class);
        } catch (JacksonException e) {
            LOGGER.error("Error converting message {} to AgreementDTO", message, e);
            throw e;
        }
    }

}
