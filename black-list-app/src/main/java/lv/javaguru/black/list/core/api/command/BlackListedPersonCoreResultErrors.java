package lv.javaguru.black.list.core.api.command;

import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lv.javaguru.black.list.core.api.dto.ValidationErrorDTO;

@Getter
@RequiredArgsConstructor
public final class BlackListedPersonCoreResultErrors extends BlackListedPersonCoreResult {

    private final List<ValidationErrorDTO> errors;

}
