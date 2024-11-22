package lv.javaguru.travel.insurance.core.underwriting.calculators.medical;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import lv.javaguru.travel.insurance.core.api.dto.PersonDTO;
import lv.javaguru.travel.insurance.core.domain.medical.TMAgeCoefficient;
import lv.javaguru.travel.insurance.core.repositories.medical.TMAgeCoefficientRepository;
import lv.javaguru.travel.insurance.core.util.DateTimeUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class TMAgeCoefficientRetriever {

    private final DateTimeUtil dateTimeUtil;
    private final TMAgeCoefficientRepository ageCoefficientRepository;

    @Value("${age.coefficient.enabled:false}")
    private Boolean ageCoefficientEnabled;

    BigDecimal setAgeCoefficient(PersonDTO person) {
        return ageCoefficientEnabled
                ? findAgeCoefficient(person)
                : setDefaultValue();
    }

    private BigDecimal findAgeCoefficient(PersonDTO person) {
        LocalDate personBirthDate = person.personBirthDate();
        LocalDate currentDate = dateTimeUtil.currentDate();
        Integer age = dateTimeUtil.calculateDifferenceBetweenDatesInYears(personBirthDate, currentDate);

        return ageCoefficientRepository.findCoefficient(age)
                .map(TMAgeCoefficient::getCoefficient)
                .orElseThrow(() -> new RuntimeException("Coefficient for age = " + age + " not found!"));
    }

    private BigDecimal setDefaultValue() {
        return BigDecimal.ONE;
    }

}
