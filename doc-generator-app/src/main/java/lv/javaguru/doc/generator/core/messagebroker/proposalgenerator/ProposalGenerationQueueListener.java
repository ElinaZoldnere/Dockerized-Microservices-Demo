package lv.javaguru.doc.generator.core.messagebroker.proposalgenerator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lv.javaguru.doc.generator.core.messagebroker.RabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(access = lombok.AccessLevel.PACKAGE)
@Slf4j
public class ProposalGenerationQueueListener {

    private final ProposalGenerator proposalGenerator;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_PROPOSAL_GENERATION)
    public void receiveMessage(String message) {
        log.info("Received message: {}", message);
        try {
            proposalGenerator.generateProposal(message);
        } catch (Exception e) {
            log.error("Error processing message: {}", message, e);
            // Wrapping in RuntimeException both checked/unchecked exceptions to trigger AMQP retry or DLQ
            throw new RuntimeException();
        }
    }

}
