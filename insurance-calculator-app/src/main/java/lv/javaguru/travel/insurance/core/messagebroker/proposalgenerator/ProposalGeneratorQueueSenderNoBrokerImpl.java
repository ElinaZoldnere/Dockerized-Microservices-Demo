package lv.javaguru.travel.insurance.core.messagebroker.proposalgenerator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lv.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile({"h2", "mysql-local"})
@RequiredArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class ProposalGeneratorQueueSenderNoBrokerImpl implements ProposalGeneratorQueueSender {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProposalGeneratorQueueSenderNoBrokerImpl.class);

    private final ObjectMapper mapper;

    @Override
    public void send(AgreementDTO agreement) {
        try {
            String agreementJson = mapper.writeValueAsString(agreement);
            LOGGER.info("Proposal generator (No-Broker) AGREEMENT: {}", agreementJson);
        } catch (JsonProcessingException e) {
            LOGGER.error("Error converting agreement to JSON", e);
        }
    }

}
