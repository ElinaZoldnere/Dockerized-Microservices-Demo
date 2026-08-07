package lv.javaguru.travel.insurance.core.messagebroker.proposalack;

import lombok.RequiredArgsConstructor;
import lv.javaguru.travel.insurance.core.api.dto.AckMessageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

@Component
@RequiredArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class MessageToAckMessageDTOConverter {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageToAckMessageDTOConverter.class);

    private final ObjectMapper mapper;

    AckMessageDTO convert(String message) {
        try {
            return mapper.readValue(message, AckMessageDTO.class);
        } catch (JacksonException e) {
            LOGGER.error("Error converting acknowledgment message {} to AckMessageDTO", message, e);
            throw e;
        }
    }

}
