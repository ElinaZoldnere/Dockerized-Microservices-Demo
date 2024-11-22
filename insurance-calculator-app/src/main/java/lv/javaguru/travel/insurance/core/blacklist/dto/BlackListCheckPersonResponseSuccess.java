package lv.javaguru.travel.insurance.core.blacklist.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public final class BlackListCheckPersonResponseSuccess extends BlackListCheckPersonResponse {

    private final String personFirstName;

    private final String personLastName;

    private final String personalCode;

    private final boolean blackListed;

}
