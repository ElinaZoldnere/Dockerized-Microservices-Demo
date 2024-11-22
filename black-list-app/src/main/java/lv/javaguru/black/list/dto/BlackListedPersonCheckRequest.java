package lv.javaguru.black.list.dto;

public record BlackListedPersonCheckRequest(

        String personFirstName,

        String personLastName,

        String personalCode) {

}
