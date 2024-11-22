package lv.javaguru.travel.insurance.core.blacklist;

import lv.javaguru.travel.insurance.core.blacklist.dto.BlackListCheckPersonRequest;

public interface BlackListCheckPersonService {

    boolean checkPerson(BlackListCheckPersonRequest request) throws Exception;

}

