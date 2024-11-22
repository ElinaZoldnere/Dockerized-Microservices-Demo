package lv.javaguru.travel.insurance.core.api.dto;

public class ValidationErrorDTOTestBuilder {
    private String errorCode;
    private String description;

    public static ValidationErrorDTOTestBuilder createValidationError() {
        return new ValidationErrorDTOTestBuilder();
    }

    public ValidationErrorDTO build() {
        return new ValidationErrorDTO(
                errorCode,
                description);
    }

    public ValidationErrorDTOTestBuilder withErrorCode(String errorCode) {
        this.errorCode = errorCode;
        return this;
    }

    public ValidationErrorDTOTestBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

}
