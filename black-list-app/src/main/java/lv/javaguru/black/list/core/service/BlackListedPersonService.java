package lv.javaguru.black.list.core.service;

import lv.javaguru.black.list.core.api.command.BlackListedPersonCoreCommand;
import lv.javaguru.black.list.core.api.command.BlackListedPersonCoreResult;

public interface BlackListedPersonService {

    BlackListedPersonCoreResult checkPerson(BlackListedPersonCoreCommand command);

}
