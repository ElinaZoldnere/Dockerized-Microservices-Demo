package lv.javaguru.travel.insurance.core.blacklist;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lv.javaguru.travel.insurance.core.blacklist.dto.BlackListCheckPersonRequest;
import lv.javaguru.travel.insurance.core.blacklist.dto.BlackListCheckPersonResponseSuccess;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile({"mysql-container", "mysql-local"})
@RequiredArgsConstructor(access = lombok.AccessLevel.PACKAGE)
@Slf4j
public class BlackListCheckPersonServiceImpl implements BlackListCheckPersonService {

    private final BlackListWebClient blackListWebClient;
    private final ObjectMapper mapper;

    @Override
    public boolean checkPerson(BlackListCheckPersonRequest request) throws Exception {
        String stringResponse = blackListWebClient.checkPerson(request);
        BlackListCheckPersonResponseSuccess successResponse = deserializeResponse(stringResponse);
        return successResponse.isBlackListed();
    }

    private BlackListCheckPersonResponseSuccess deserializeResponse(String stringResponse) throws Exception {
        try {
            return mapper.readValue(stringResponse, BlackListCheckPersonResponseSuccess.class);
        } catch (JsonMappingException e) {
            log.error("Can not deserialize into BlackListCheckPersonResponseSuccess. "
                            + "Unexpected response or validation error from BlackList service. Raw content: {}, {}",
                    stringResponse, e.getMessage());
            throw e;
        }
    }

}
