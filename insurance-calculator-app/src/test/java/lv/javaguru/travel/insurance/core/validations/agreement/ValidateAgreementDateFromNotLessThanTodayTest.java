package lv.javaguru.travel.insurance.core.validations.agreement;

import java.util.Optional;
import lv.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import lv.javaguru.travel.insurance.core.api.dto.AgreementDTOTestBuilder;
import lv.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import lv.javaguru.travel.insurance.core.util.DateHelper;
import lv.javaguru.travel.insurance.core.util.DateTimeUtil;
import lv.javaguru.travel.insurance.core.validations.ValidationErrorFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ValidateAgreementDateFromNotLessThanTodayTest {

    @Mock
    private DateTimeUtil dateTimeUtil;
    @Mock
    private ValidationErrorFactory errorMock;

    @InjectMocks
    private ValidateAgreementDateFromNotLessThanToday validate;
    @InjectMocks
    private DateHelper helper;

    @Test
    void validateShouldReturnErrorWhenAgreementDateFromLessThanToday() {
        AgreementDTO agreement = AgreementDTOTestBuilder.createAgreement()
                .withDateFrom(helper.newDate("2025.06.10"))
                .withDateTo(helper.newDate("2025.06.11"))
                .build();

        when(dateTimeUtil.currentDate()).thenReturn(helper.newDate("2025.06.11"));
        when(errorMock.buildError("ERROR_CODE_11"))
                .thenReturn(new ValidationErrorDTO("ERROR_CODE_11",
                        "Field agreementDateFrom is in the past!"));

        Optional<ValidationErrorDTO> result = validate.validateSingle(agreement);

        assertThat(result)
                .isPresent()
                .hasValueSatisfying(error -> {
                    assertThat(error.errorCode()).isEqualTo("ERROR_CODE_11");
                    assertThat(error.description()).isEqualTo("Field agreementDateFrom is in the past!");
                });
    }

}
