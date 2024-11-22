package lv.javaguru.travel.insurance.core.messagebroker.proposalack;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lv.javaguru.travel.insurance.core.messagebroker.RabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

@Component
@Profile("mysql-container")
@RequiredArgsConstructor(access = lombok.AccessLevel.PACKAGE)
@Slf4j
public class ProposalGenerationAckQueueListener {

    private final ProposalGenerationAck proposalAck;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_PROPOSAL_GENERATION_ACK)
    public void receiveMessage(String message) {
        log.info("Received proposal generation acknowledgment message: {}", message);
        try {
            proposalAck.saveAck(message);
        } catch (DataIntegrityViolationException dataEx) {
            log.warn("Acknowledgment already exists, skipping retry for message: {}", message);
        } catch (Exception e) {
            log.error("Error saving proposal generation acknowledgment message: {}", message, e);
            // Wrapping in RuntimeException both checked/unchecked exceptions to trigger AMQP retry or DLQ
            throw new RuntimeException();
        }
    }

}
