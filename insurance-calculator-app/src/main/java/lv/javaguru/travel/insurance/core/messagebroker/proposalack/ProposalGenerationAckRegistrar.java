package lv.javaguru.travel.insurance.core.messagebroker.proposalack;

import lombok.RequiredArgsConstructor;
import lv.javaguru.travel.insurance.core.api.dto.AckMessageDTO;
import lv.javaguru.travel.insurance.core.domain.entities.GeneratedProposalAckEntity;
import lv.javaguru.travel.insurance.core.repositories.entities.GeneratedProposalAckEntityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class ProposalGenerationAckRegistrar {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProposalGenerationAckRegistrar.class);

    private final GeneratedProposalAckEntityRepository repository;

    void register(AckMessageDTO ackMessageDTO) throws DataIntegrityViolationException {
        GeneratedProposalAckEntity entity = new GeneratedProposalAckEntity();
        entity.setUuid(ackMessageDTO.uuid());
        entity.setGeneratedAt(ackMessageDTO.timestamp());
        entity.setDirectoryPath(ackMessageDTO.path());
        try {
            repository.save(entity);
        } catch (DataIntegrityViolationException e) {
            LOGGER.warn("Unique constraint violation. "
                    + "Proposal generation acknowledgment with uuid {} already registered.", entity.getUuid(), e);
            throw e;
        }
    }

}
