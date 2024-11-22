package lv.javaguru.travel.insurance.core.services;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lv.javaguru.travel.insurance.core.api.command.TravelCalculatePremiumCoreCommand;
import lv.javaguru.travel.insurance.core.api.command.TravelCalculatePremiumCoreResult;
import lv.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import lv.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import lv.javaguru.travel.insurance.core.messagebroker.proposalgenerator.ProposalGeneratorQueueSender;
import lv.javaguru.travel.insurance.core.services.writers.entity.EntityWriter;
import lv.javaguru.travel.insurance.core.validations.TravelAgreementValidator;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
@RequiredArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class TravelCalculatePremiumServiceImpl implements TravelCalculatePremiumService {

    private final TravelAgreementValidator agreementValidator;
    private final CalculateAndUpdateAgreementWithPremiums calculateAndUpdateAgreement;
    private final EntityWriter entityWriter;
    private final ProposalGeneratorQueueSender sender;

    @Override
    public TravelCalculatePremiumCoreResult calculatePremium(TravelCalculatePremiumCoreCommand command) {
        List<ValidationErrorDTO> errors = agreementValidator.validate(command.getAgreement());
        return errors.isEmpty()
                ? buildPremiumResponse(command.getAgreement())
                : buildErrorResponse(errors);
    }

    private TravelCalculatePremiumCoreResult buildPremiumResponse(AgreementDTO agreement) {
        AgreementDTO agreementWithPremiums = calculateAndUpdateAgreement.calculateAgreementPremiums(agreement);
        entityWriter.writeEntities(agreementWithPremiums);
        TravelCalculatePremiumCoreResult coreResult = new TravelCalculatePremiumCoreResult();
        coreResult.setAgreement(agreementWithPremiums);
        sender.send(agreementWithPremiums);
        return coreResult;
    }

    private TravelCalculatePremiumCoreResult buildErrorResponse(List<ValidationErrorDTO> errors) {
        return new TravelCalculatePremiumCoreResult(errors);
    }

}
