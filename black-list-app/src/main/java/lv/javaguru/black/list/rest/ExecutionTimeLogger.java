package lv.javaguru.black.list.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
class ExecutionTimeLogger {

    void log(long executionTime) {
        log.info("Request processing time (ms): {}", executionTime);
    }

}
