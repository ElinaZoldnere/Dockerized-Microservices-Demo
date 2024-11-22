package lv.javaguru.travel.insurance.core.api.command;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lv.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import lv.javaguru.travel.insurance.dto.serialize.AgreementSerialDTO;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TravelGetAgreementCoreResult {

    private List<ValidationErrorDTO> errors;

    private AgreementSerialDTO agreement;

    public boolean hasErrors() {
        return errors != null && !errors.isEmpty();
    }

    public TravelGetAgreementCoreResult(List<ValidationErrorDTO> errors) {
        this.errors = errors;
    }

}
