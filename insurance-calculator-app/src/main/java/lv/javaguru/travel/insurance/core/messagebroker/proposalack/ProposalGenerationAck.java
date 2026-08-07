package lv.javaguru.travel.insurance.core.messagebroker.proposalack;

import org.springframework.dao.DataIntegrityViolationException;

public interface ProposalGenerationAck {
    void saveAck(String message) throws DataIntegrityViolationException;

}
