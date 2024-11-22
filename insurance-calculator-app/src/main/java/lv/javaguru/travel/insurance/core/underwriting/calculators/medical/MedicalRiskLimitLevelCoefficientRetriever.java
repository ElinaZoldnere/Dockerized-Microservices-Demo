package lv.javaguru.travel.insurance.core.underwriting.calculators.medical;

import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import lv.javaguru.travel.insurance.core.api.dto.PersonDTO;
import lv.javaguru.travel.insurance.core.domain.medical.MedicalRiskLimitLevel;
import lv.javaguru.travel.insurance.core.repositories.medical.MedicalRiskLimitLevelRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class MedicalRiskLimitLevelCoefficientRetriever {

    private final MedicalRiskLimitLevelRepository medicalRiskLimitLevelRepository;

    @Value("${medical.risk.limit.level.enabled:false}")
    private Boolean medicalRiskLimitLevelEnabled;

    BigDecimal setLimitLevelCoefficient(PersonDTO person) {
        return medicalRiskLimitLevelEnabled
                ? findLimitLevelCoefficient(person)
                : setDefaultValue();
    }

    private BigDecimal findLimitLevelCoefficient(PersonDTO person) {
        String medicalRiskLimitLevelIc = person.medicalRiskLimitLevel();
        return medicalRiskLimitLevelRepository.findByMedicalRiskLimitLevelIc(medicalRiskLimitLevelIc)
                .map(MedicalRiskLimitLevel::getCoefficient)
                .orElseThrow(() -> new RuntimeException(
                        "Medical risk limit level = " + medicalRiskLimitLevelIc + " coefficient not found!"));
    }

    private BigDecimal setDefaultValue() {
        return BigDecimal.ONE;
    }

}
