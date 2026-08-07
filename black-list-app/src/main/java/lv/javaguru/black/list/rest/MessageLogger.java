package lv.javaguru.black.list.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

@Component
@RequiredArgsConstructor(access = lombok.AccessLevel.PACKAGE)
@Slf4j
class MessageLogger {

    private final ObjectMapper mapper;

    void log(String prefix, Object obj) {
        try {
            String objToJson = mapper.writeValueAsString(obj);
            log.info("{}: {}", prefix, objToJson);
        } catch (JacksonException e) {
            log.error("Error converting {} to JSON", prefix, e);
        }
    }

}
