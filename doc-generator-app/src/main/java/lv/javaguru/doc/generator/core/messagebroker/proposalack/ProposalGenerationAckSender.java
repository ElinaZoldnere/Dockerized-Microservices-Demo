package lv.javaguru.doc.generator.core.messagebroker.proposalack;

import lv.javaguru.doc.generator.core.api.dto.AckMessageDTO;

public interface ProposalGenerationAckSender {
    void sendProposalGenerationAck(AckMessageDTO ackMessageDTO);

}
