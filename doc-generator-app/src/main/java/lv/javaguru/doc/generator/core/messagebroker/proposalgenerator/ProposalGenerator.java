package lv.javaguru.doc.generator.core.messagebroker.proposalgenerator;

import java.io.IOException;

public interface ProposalGenerator {
    void generateProposal(String message) throws IOException;

}
