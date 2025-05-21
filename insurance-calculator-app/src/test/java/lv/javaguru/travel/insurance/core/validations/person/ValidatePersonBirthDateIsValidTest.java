package lv.javaguru.travel.insurance.core.validations.person;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Stream;
import lv.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import lv.javaguru.travel.insurance.core.api.dto.AgreementDTOTestBuilder;
import lv.javaguru.travel.insurance.core.api.dto.PersonDTO;
import lv.javaguru.travel.insurance.core.api.dto.PersonDTOTestBuilder;
import lv.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import lv.javaguru.travel.insurance.core.util.DateHelper;
import lv.javaguru.travel.insurance.core.util.DateTimeUtil;
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
class ValidatePersonBirthDateIsValidTest {

    @Mock
    private DateTimeUtil dateTimeUtil;
    @Mock
    private ValidationErrorFactory errorMock;

    @InjectMocks
    private ValidatePersonBirthDateIsValid validate;

    private static DateHelper helper;

    @BeforeAll
    static void setUp() {
        helper = new DateHelper();
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("birthDateValue")
    public void validateShouldReturnErrorWhenPersonBirthDateIsNotValid(String testName, LocalDate birthDate) {
        AgreementDTO agreement = AgreementDTOTestBuilder.createAgreement().build();
        PersonDTO person = PersonDTOTestBuilder.createPerson()
                .withPersonBirthdate(birthDate)
                .build();

        when(dateTimeUtil.currentDate()).thenReturn(helper.newDate("2025.06.11"));
        when(dateTimeUtil.subtractYears(any(LocalDate.class), eq(150))).thenReturn(helper.newDate("1875.03.11"));
        when(errorMock.buildError("ERROR_CODE_14"))
                .thenReturn(new ValidationErrorDTO("ERROR_CODE_14",
                        "PersonBirthDate is not a valid date!"));

        Optional<ValidationErrorDTO> result = validate.validateSingle(agreement, person);

        assertThat(result)
                .isPresent()
                .hasValueSatisfying(error -> {
                    assertThat(error.errorCode()).isEqualTo("ERROR_CODE_14");
                    assertThat(error.description()).isEqualTo("PersonBirthDate is not a valid date!");
                });
    }

    private static Stream<Arguments> birthDateValue() {
        return Stream.of(
                Arguments.of("BirthDate is after current date", helper.newDate("2030.03.11")),
                Arguments.of("BirthDate is less than minimal", helper.newDate("1800.03.11"))
        );
    }

}
