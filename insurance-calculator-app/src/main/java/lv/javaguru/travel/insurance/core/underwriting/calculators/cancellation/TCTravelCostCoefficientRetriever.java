package lv.javaguru.travel.insurance.core.underwriting.calculators.cancellation;

import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import lv.javaguru.travel.insurance.core.api.dto.PersonDTO;
import lv.javaguru.travel.insurance.core.domain.cancellation.TCTravelCostCoefficient;
import lv.javaguru.travel.insurance.core.repositories.cancellation.TCTravelCostCoefficientRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class TCTravelCostCoefficientRetriever {

    private final TCTravelCostCoefficientRepository repository;

    BigDecimal findTravelCostCoefficient(PersonDTO person) {
        BigDecimal travelCost = person.travelCost();
        return repository.findCoefficientByTravelCost(travelCost)
                .map(TCTravelCostCoefficient::getCoefficient)
                .orElseThrow(() -> new RuntimeException("Coefficient for travel cost = " + travelCost + " not found!"));
    }

}
