package lv.javaguru.travel.insurance.core.messagebroker;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableRabbit
@Configuration
@SuppressWarnings("checkstyle:multiplestringliterals")
@Slf4j
public class RabbitMQConfig {

    public static final String QUEUE_PROPOSAL_GENERATION = "q.proposal-generation";
    public static final String QUEUE_PROPOSAL_GENERATION_DLX = "dlx.proposal-generation";
    public static final String QUEUE_PROPOSAL_GENERATION_DLX_ROUTING_KEY = "dlx.routing-key.proposal-generation";

    public static final String QUEUE_PROPOSAL_GENERATION_ACK = "q.proposal-generation-ack";
    public static final String QUEUE_PROPOSAL_GENERATION_ACK_DLX = "dlx.proposal-generation-ack";
    public static final String QUEUE_PROPOSAL_GENERATION_ACK_DLX_ROUTING_KEY =
            "dlx.routing-key.proposal-generation-ack";
    public static final String QUEUE_PROPOSAL_GENERATION_ACK_DLQ = "dlq.proposal-generation-ack";

    @Bean
    public Queue proposalGenerationQueue() {
        return QueueBuilder.durable(QUEUE_PROPOSAL_GENERATION)
                .withArgument("x-dead-letter-exchange", QUEUE_PROPOSAL_GENERATION_DLX)
                .withArgument("x-dead-letter-routing-key", QUEUE_PROPOSAL_GENERATION_DLX_ROUTING_KEY)
                .build();
    }

    @Bean
    public Queue proposalGenerationAckQueue() {
        return QueueBuilder.durable(QUEUE_PROPOSAL_GENERATION_ACK)
                .withArgument("x-dead-letter-exchange", QUEUE_PROPOSAL_GENERATION_ACK_DLX)
                .withArgument("x-dead-letter-routing-key", QUEUE_PROPOSAL_GENERATION_ACK_DLX_ROUTING_KEY)
                .build();
    }

    @Bean
    public Queue proposalGenerationAckDeadLetterQueue() {
        return QueueBuilder.durable(QUEUE_PROPOSAL_GENERATION_ACK_DLQ).build();
    }

    @Bean
    public DirectExchange proposalGenerationDeadLetterExchange() {
        return new DirectExchange(QUEUE_PROPOSAL_GENERATION_DLX);
    }

    @Bean
    public Binding bindProposalGenerationAckQueueToDeadLetterExchange() {
        return BindingBuilder.bind(proposalGenerationAckDeadLetterQueue())
                .to(proposalGenerationDeadLetterExchange())
                .with(QUEUE_PROPOSAL_GENERATION_ACK_DLX_ROUTING_KEY);
    }

}
