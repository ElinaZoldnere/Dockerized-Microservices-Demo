package lv.javaguru.travel.insurance.core.validations.agreement;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Stream;
import lv.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import lv.javaguru.travel.insurance.core.api.dto.AgreementDTOTestBuilder;
import lv.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import lv.javaguru.travel.insurance.core.util.DateHelper;
import lv.javaguru.travel.insurance.core.validations.ValidationErrorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ValidateAgreementDateChronologyTest {

    @Mock
    private ValidationErrorFactory errorMock;

    @InjectMocks
    private ValidateAgreementDateChronology validate;

    private static DateHelper helper;

    @BeforeAll
    static void setUp() {
        helper = new DateHelper();
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("agreementDateToValue")
    void validateShouldReturnErrorWhenAgreementDateChronologyIsWrong(String testName, LocalDate agreementDateTo) {
        helper = new DateHelper();
        AgreementDTO agreement = AgreementDTOTestBuilder.createAgreement()
                .withDateFrom(helper.newDate("2025.06.10"))
                .withDateTo(agreementDateTo)
                .build();

        when(errorMock.buildError("ERROR_CODE_13"))
                .thenReturn(new ValidationErrorDTO("ERROR_CODE_13",
                        "AgreementDateTo must be after AgreementDateFrom!"));

        Optional<ValidationErrorDTO> result = validate.validateSingle(agreement);

        assertThat(result)
                .isPresent()
                .hasValueSatisfying(error -> {
                    assertThat(error.errorCode()).isEqualTo("ERROR_CODE_13");
                    assertThat(error.description()).isEqualTo(
                            "AgreementDateTo must be after AgreementDateFrom!");
                });
    }

    private static Stream<Arguments> agreementDateToValue() {
        return Stream.of(
                Arguments.of("AgreementDateTo equals agreementDateFrom",
                        helper.newDate("2025.06.10")),
                Arguments.of("AgreementDateTo less than agreementDateFrom",
                        helper.newDate("2025.06.09"))
        );
    }

}
