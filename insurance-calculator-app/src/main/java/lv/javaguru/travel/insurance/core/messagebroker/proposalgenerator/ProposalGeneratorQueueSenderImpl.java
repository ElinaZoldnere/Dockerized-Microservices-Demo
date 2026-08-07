package lv.javaguru.travel.insurance.core.messagebroker.proposalgenerator;

import lombok.RequiredArgsConstructor;
import lv.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

@Component
@Profile("mysql-container")
@RequiredArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class ProposalGeneratorQueueSenderImpl implements ProposalGeneratorQueueSender {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProposalGeneratorQueueSenderImpl.class);

    private final ObjectMapper mapper;
    private final RabbitTemplate rabbitTemplate;

    @Override
    public void send(AgreementDTO agreement) {
        try {
            String agreementToJson = mapper.writeValueAsString(agreement);
            rabbitTemplate.convertAndSend("q.proposal-generation", agreementToJson);
            LOGGER.info("Proposal Generator: sent agreement UUID {}, content: {}", agreement.uuid(), agreementToJson);
        } catch (JacksonException e) {
            LOGGER.error("Error converting agreement UUID {} to JSON", agreement.uuid(), e);
        } catch (AmqpException e) {
            LOGGER.error("Error sending agreement UUID {} to queue", agreement.uuid(), e);
        }
    }

}
