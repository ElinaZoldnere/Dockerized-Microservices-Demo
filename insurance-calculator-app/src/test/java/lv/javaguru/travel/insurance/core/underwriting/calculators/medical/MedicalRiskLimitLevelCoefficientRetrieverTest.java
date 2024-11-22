package lv.javaguru.travel.insurance.core.underwriting.calculators.medical;

import java.math.BigDecimal;
import java.util.Optional;
import lv.javaguru.travel.insurance.core.api.dto.PersonDTO;
import lv.javaguru.travel.insurance.core.api.dto.PersonDTOTestBuilder;
import lv.javaguru.travel.insurance.core.domain.medical.MedicalRiskLimitLevel;
import lv.javaguru.travel.insurance.core.repositories.medical.MedicalRiskLimitLevelRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MedicalRiskLimitLevelCoefficientRetrieverTest {

    @Mock
    private MedicalRiskLimitLevelRepository limitLevelRepositoryMock;

    @InjectMocks
    private MedicalRiskLimitLevelCoefficientRetriever limitLevelCoefficientRetriever;

    @Test
    void setLimitLevelCoefficientShouldFindCoefficientWhenCoefficientExists() {
        ReflectionTestUtils.setField(limitLevelCoefficientRetriever, "medicalRiskLimitLevelEnabled", Boolean.TRUE);
        PersonDTO person = PersonDTOTestBuilder.createPerson()
                .withMedicalRiskLimitLevel("LEVEL_10000")
                .build();
        BigDecimal limitLevelCoefficient = BigDecimal.valueOf(1.2);

        MedicalRiskLimitLevel limitLevelMock = mock(MedicalRiskLimitLevel.class);
        when(limitLevelRepositoryMock.findByMedicalRiskLimitLevelIc(any()))
                .thenReturn(Optional.of(limitLevelMock));
        when(limitLevelMock.getCoefficient()).thenReturn(limitLevelCoefficient);

        BigDecimal actualLimitLevelCoefficient = limitLevelCoefficientRetriever.setLimitLevelCoefficient(person);

        assertThat(actualLimitLevelCoefficient).isEqualTo(limitLevelCoefficient);
    }

    @Test
    void setLimitLevelCoefficientShouldThrowExceptionWhenCoefficientDoesNotExist() {
        ReflectionTestUtils.setField(limitLevelCoefficientRetriever, "medicalRiskLimitLevelEnabled", Boolean.TRUE);
        PersonDTO person = PersonDTOTestBuilder.createPerson()
                .withMedicalRiskLimitLevel("INVALID")
                .build();

        when(limitLevelRepositoryMock.findByMedicalRiskLimitLevelIc("INVALID"))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> limitLevelCoefficientRetriever.setLimitLevelCoefficient(person))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Medical risk limit level = INVALID coefficient not found!");
    }

    @Test
    void seLimitLevelCoefficientShouldReturnDefaultValueWhenRiskLimitLevelDisabled() {
        ReflectionTestUtils.setField(limitLevelCoefficientRetriever, "medicalRiskLimitLevelEnabled", Boolean.FALSE);
        PersonDTO person = PersonDTOTestBuilder.createPerson().build();

        BigDecimal actualLimitLevelCoefficient = limitLevelCoefficientRetriever.setLimitLevelCoefficient(person);

        assertThat(actualLimitLevelCoefficient).isEqualTo(BigDecimal.ONE);
    }

}
