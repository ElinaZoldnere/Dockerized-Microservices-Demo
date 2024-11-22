package lv.javaguru.travel.insurance.core.underwriting.calculators.cancellation;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import lv.javaguru.travel.insurance.core.api.dto.PersonDTO;
import lv.javaguru.travel.insurance.core.domain.cancellation.TCAgeCoefficient;
import lv.javaguru.travel.insurance.core.repositories.cancellation.TCAgeCoefficientRepository;
import lv.javaguru.travel.insurance.core.util.DateTimeUtil;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class TCAgeCoefficientRetriever {

    private final DateTimeUtil dateTimeUtil;
    private final TCAgeCoefficientRepository repository;

    BigDecimal findAgeCoefficient(PersonDTO person) {
        LocalDate personBirthDate = person.personBirthDate();
        LocalDate currentDate = dateTimeUtil.currentDate(); // hours and seconds does not matter
        Integer age = dateTimeUtil.calculateDifferenceBetweenDatesInYears(personBirthDate, currentDate);

        return repository.findCoefficientByAge(age)
                .map(TCAgeCoefficient::getCoefficient)
                .orElseThrow(() -> new RuntimeException("Coefficient for age = " + age + " not found!"));
    }

}
