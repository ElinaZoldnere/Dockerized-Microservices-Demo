package lv.javaguru.black.list.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public final class BlackListedPersonCheckResponseSuccess extends BlackListedPersonCheckResponse {

    private final String personFirstName;

    private final String personLastName;

    private final String personalCode;

    private final boolean blackListed;

}
