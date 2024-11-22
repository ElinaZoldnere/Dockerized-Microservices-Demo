package lv.javaguru.black.list.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(access = lombok.AccessLevel.PACKAGE)
@Slf4j
class MessageLogger {

    private final ObjectMapper mapper;

    void log(String prefix, Object obj) {
        try {
            String objToJson = mapper.writeValueAsString(obj);
            log.info("{}: {}", prefix, objToJson);
        } catch (JsonProcessingException e) {
            log.error("Error converting {} to JSON", prefix, e);
        }
    }

}
