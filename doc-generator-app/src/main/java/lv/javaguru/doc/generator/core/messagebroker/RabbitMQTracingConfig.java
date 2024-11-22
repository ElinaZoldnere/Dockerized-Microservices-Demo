package lv.javaguru.doc.generator.core.messagebroker;

import brave.Tracing;
import brave.messaging.MessagingTracing;
import brave.spring.rabbit.SpringRabbitTracing;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.boot.autoconfigure.amqp.RabbitTemplateCustomizer;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQTracingConfig {

    @Bean
    public MessagingTracing messagingTracing(Tracing tracing) {
        return MessagingTracing.create(tracing);
    }

    @Bean
    public SpringRabbitTracing springRabbitTracing(MessagingTracing messagingTracing) {
        return SpringRabbitTracing.newBuilder(messagingTracing)
                .build();
    }

    @Bean
    public RabbitTemplateCustomizer rabbitTemplateCustomizer(SpringRabbitTracing springRabbitTracing) {
        return springRabbitTracing::decorateRabbitTemplate;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory,
            SimpleRabbitListenerContainerFactoryConfigurer configurer,
            SpringRabbitTracing springRabbitTracing) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        return springRabbitTracing.decorateRabbitListenerContainerFactory(factory);
    }

}
