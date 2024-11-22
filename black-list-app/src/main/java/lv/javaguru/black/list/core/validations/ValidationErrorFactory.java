package lv.javaguru.black.list.core.validations;

import lombok.RequiredArgsConstructor;
import lv.javaguru.black.list.core.api.dto.ValidationErrorDTO;
import lv.javaguru.black.list.core.validations.util.ErrorCodeUtil;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(access = lombok.AccessLevel.PACKAGE)
public class ValidationErrorFactory {

    private final ErrorCodeUtil errorCodeUtil;

    public ValidationErrorDTO buildError(String errorCode) {
        String errorDescription = errorCodeUtil.getErrorDescription(errorCode);
        return new ValidationErrorDTO(errorCode, errorDescription);
    }

}
