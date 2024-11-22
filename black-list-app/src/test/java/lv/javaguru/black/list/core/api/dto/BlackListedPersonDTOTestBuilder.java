package lv.javaguru.black.list.core.api.dto;

public class BlackListedPersonDTOTestBuilder {
    private String personFirstName;
    private String personLastName;
    private String personalCode;

    public static BlackListedPersonDTOTestBuilder createBlackListedPerson() {
        return new BlackListedPersonDTOTestBuilder();
    }

    public PersonDTO build() {
        return new PersonDTO(
                personFirstName,
                personLastName,
                personalCode
        );
    }

    public BlackListedPersonDTOTestBuilder withPersonFirstName(String personFirstName) {
        this.personFirstName = personFirstName;
        return this;
    }

    public BlackListedPersonDTOTestBuilder withPersonLastName(String personLastName) {
        this.personLastName = personLastName;
        return this;
    }

    public BlackListedPersonDTOTestBuilder withPersonalCode(String personalCode) {
        this.personalCode = personalCode;
        return this;
    }

}
