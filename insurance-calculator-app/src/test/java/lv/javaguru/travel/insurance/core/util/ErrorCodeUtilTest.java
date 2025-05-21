package lv.javaguru.travel.insurance.core.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class ErrorCodeUtilTest {

    ErrorCodeUtil errorCodeUtil = new ErrorCodeUtil();

    @Test
    void getErrorDescriptionShouldReturnCorrectDescription() {
        String errorCode = "ERROR_CODE_1";
        String expectedDescription = "Field personFirstName is empty!";

        String actualDescription = errorCodeUtil.getErrorDescription(errorCode);

        assertThat(actualDescription).isEqualTo(expectedDescription);
    }

    @Test
    void getErrorDescriptionWithPlaceholdersShouldReturnCorrectDescription() {
        String errorCode = "ERROR_CODE_22";
        String expectedDescriptionWithPlaceholders = "Uuid 0000 is not in database!";
        List<Placeholder> placeholders = List.of(
                new Placeholder("NOT_EXISTING_UUID", "0000"));

        String actualDescription = errorCodeUtil.getErrorDescription(errorCode, placeholders);

        assertThat(actualDescription).isEqualTo(expectedDescriptionWithPlaceholders);
    }

    @Test
    void getErrorDescriptionShouldNotReturnDescriptionFromInvalidCode() {
        String errorCode = "INVALID_CODE";

        String actualDescription = errorCodeUtil.getErrorDescription(errorCode);

        assertThat(actualDescription).isNull();
    }

}
