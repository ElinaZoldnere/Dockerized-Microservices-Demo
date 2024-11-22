package lv.javaguru.doc.generator.core.messagebroker.proposalgenerator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lv.javaguru.doc.generator.core.api.dto.AgreementDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class MessageToAgreementDTOConverter {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageToAgreementDTOConverter.class);

    private final ObjectMapper mapper;

    AgreementDTO convert(String message) throws JsonProcessingException {
        try {
            return mapper.readValue(message, AgreementDTO.class);
        } catch (JsonProcessingException e) {
            LOGGER.error("Error converting message {} to AgreementDTO", message, e);
            throw e;
        }
    }

}
