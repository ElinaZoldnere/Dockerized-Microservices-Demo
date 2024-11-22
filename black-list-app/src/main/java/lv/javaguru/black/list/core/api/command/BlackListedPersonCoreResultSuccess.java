package lv.javaguru.black.list.core.api.command;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lv.javaguru.black.list.core.api.dto.PersonDTO;

@Getter
@RequiredArgsConstructor
public final class BlackListedPersonCoreResultSuccess extends BlackListedPersonCoreResult {

    private final PersonDTO person;

    private final boolean blackListed;

}
