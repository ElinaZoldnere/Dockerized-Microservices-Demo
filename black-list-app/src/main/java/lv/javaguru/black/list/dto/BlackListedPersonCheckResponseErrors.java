package lv.javaguru.black.list.dto;

import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public final class BlackListedPersonCheckResponseErrors extends BlackListedPersonCheckResponse {

    private final List<ValidationError> errors;

}
