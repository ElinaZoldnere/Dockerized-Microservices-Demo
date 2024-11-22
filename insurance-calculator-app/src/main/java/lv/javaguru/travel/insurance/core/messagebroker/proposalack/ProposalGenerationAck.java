package lv.javaguru.travel.insurance.core.messagebroker.proposalack;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.dao.DataIntegrityViolationException;

public interface ProposalGenerationAck {
    void saveAck(String message) throws JsonProcessingException, DataIntegrityViolationException;

}
