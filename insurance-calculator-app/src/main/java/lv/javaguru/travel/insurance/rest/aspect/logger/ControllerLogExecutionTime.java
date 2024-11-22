package lv.javaguru.travel.insurance.rest.aspect.logger;

import lombok.experimental.UtilityClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@UtilityClass
final class ControllerLogExecutionTime {

    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerLogExecutionTime.class);

    static void log(long executionTime) {
        LOGGER.info("Request processing time (ms): {}", executionTime);
    }

}
