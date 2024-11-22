package lv.javaguru.doc.generator.core.messagebroker.proposalack;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lv.javaguru.doc.generator.core.api.dto.AckMessageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class ProposalGenerationAckSenderImpl implements ProposalGenerationAckSender {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProposalGenerationAckSenderImpl.class);

    private final ObjectMapper mapper;
    private final RabbitTemplate rabbitTemplate;

    @Override
    public void sendProposalGenerationAck(AckMessageDTO ackMessageDTO) {
        try {
            String ackMessageToJson = mapper.writeValueAsString(ackMessageDTO);

            rabbitTemplate.convertAndSend("q.proposal-generation-ack", ackMessageToJson);
            LOGGER.info("Sent PDF generation acknowledgment for proposal: uuid {}, path: {}", ackMessageDTO.uuid(),
                    ackMessageDTO.path());

        } catch (JsonProcessingException e) {
            LOGGER.error("Error converting acknowledgment message to JSON for agreement UUID {}",
                    ackMessageDTO.uuid(), e);

        } catch (AmqpException e) {
            LOGGER.error("Error sending acknowledgment message for agreement UUID {} to queue",
                    ackMessageDTO.uuid(), e);
        }
    }

}
