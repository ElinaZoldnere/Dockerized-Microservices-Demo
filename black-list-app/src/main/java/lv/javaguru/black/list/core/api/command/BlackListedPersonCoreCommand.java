package lv.javaguru.black.list.core.api.command;

import lv.javaguru.black.list.core.api.dto.PersonDTO;

public record BlackListedPersonCoreCommand(

        PersonDTO person) {

}
