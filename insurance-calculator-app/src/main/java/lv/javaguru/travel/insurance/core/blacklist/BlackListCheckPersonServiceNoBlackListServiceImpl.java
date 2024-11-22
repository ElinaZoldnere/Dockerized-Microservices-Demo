package lv.javaguru.travel.insurance.core.blacklist;

import lombok.extern.slf4j.Slf4j;
import lv.javaguru.travel.insurance.core.blacklist.dto.BlackListCheckPersonRequest;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("h2")
@Slf4j
public class BlackListCheckPersonServiceNoBlackListServiceImpl implements BlackListCheckPersonService {

    @Override
    public boolean checkPerson(BlackListCheckPersonRequest request) throws Exception {
        log.info("Application running without actual Blacklist service. Return value is always false.");
        return false;
    }

}
