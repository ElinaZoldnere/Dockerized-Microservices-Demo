package lv.javaguru.travel.insurance.core.blacklist.dto;

import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public final class BlackListCheckPersonResponseErrors extends BlackListCheckPersonResponse {

    private final List<ValidationError> errors;

}
