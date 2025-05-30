package lv.javaguru.travel.insurance.core.underwriting.calculators.cancellation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;
import lv.javaguru.travel.insurance.core.api.dto.PersonDTO;
import lv.javaguru.travel.insurance.core.api.dto.PersonDTOTestBuilder;
import lv.javaguru.travel.insurance.core.domain.cancellation.TCAgeCoefficient;
import lv.javaguru.travel.insurance.core.repositories.cancellation.TCAgeCoefficientRepository;
import lv.javaguru.travel.insurance.core.util.DateHelper;
import lv.javaguru.travel.insurance.core.util.DateTimeUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TCAgeCoefficientRetrieverTest {

    @Mock
    private DateTimeUtil dateTimeUtilMock;
    @Mock
    private TCAgeCoefficientRepository repositoryMock;

    @InjectMocks
    private TCAgeCoefficientRetriever retriever;

    @InjectMocks
    private DateHelper helper;

    @Test
    void findAgeCoefficientShouldFindCoefficientWhenCoefficientExists() {
        BigDecimal ageCoefficient = BigDecimal.valueOf(30);
        PersonDTO person = PersonDTOTestBuilder.createPerson()
                .withPersonBirthdate(helper.newDate("1994.01.01"))
                .build();

        TCAgeCoefficient ageCoefficientMock = mock(TCAgeCoefficient.class);
        when(repositoryMock.findCoefficientByAge(any()))
                .thenReturn(Optional.of(ageCoefficientMock));
        when(ageCoefficientMock.getCoefficient()).thenReturn(ageCoefficient);

        BigDecimal actualAgeCoefficient = retriever.findAgeCoefficient(person);

        assertThat(actualAgeCoefficient).isEqualTo(ageCoefficient);
    }

    @Test
    void findAgeCoefficientShouldThrowExceptionWhenCoefficientDoesNotExist() {
        PersonDTO person = PersonDTOTestBuilder.createPerson()
                .build();

        when(dateTimeUtilMock.calculateDifferenceBetweenDatesInYears(any(), any())).thenReturn(160);
        when(repositoryMock.findCoefficientByAge(any()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> retriever.findAgeCoefficient(person))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Coefficient for age = 160 not found!");
    }

}
