package lv.javaguru.travel.insurance.core.blacklist.dto;

public record BlackListCheckPersonRequest(

        String personFirstName,

        String personLastName,

        String personalCode) {

}
