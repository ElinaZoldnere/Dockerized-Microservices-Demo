package lv.javaguru.travel.insurance.core.messagebroker.proposalgenerator;

import lv.javaguru.travel.insurance.core.api.dto.AgreementDTO;

public interface ProposalGeneratorQueueSender {
    void send(AgreementDTO agreement);
}
