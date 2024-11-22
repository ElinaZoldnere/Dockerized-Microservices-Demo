package lv.javaguru.travel.insurance.core.messagebroker.proposalack;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lv.javaguru.travel.insurance.core.api.dto.AckMessageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class ProposalGenerationAckImpl implements ProposalGenerationAck {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProposalGenerationAckImpl.class);

    private final MessageToAckMessageDTOConverter converter;
    private final ProposalGenerationAckRegistrar registrar;

    @Override
    public void saveAck(String message) throws JsonProcessingException, DataIntegrityViolationException {
        AckMessageDTO ackMessageDTO = converter.convert(message);
        registrar.register(ackMessageDTO);
    }

}
