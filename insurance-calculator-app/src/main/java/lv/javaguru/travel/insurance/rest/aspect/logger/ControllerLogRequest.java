package lv.javaguru.travel.insurance.rest.aspect.logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lv.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;
import lv.javaguru.travel.insurance.dto.v2.TravelCalculatePremiumRequestV2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class ControllerLogRequest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerLogRequest.class);

    private final ObjectMapper mapper;

    public void log(ProceedingJoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        if (args.length > 0) {
            Object request = args[0];
            if (request instanceof TravelCalculatePremiumRequestV1
                    || request instanceof TravelCalculatePremiumRequestV2) {
                try {
                    String requestJson = mapper.writeValueAsString(request);
                    logRequestContents(requestJson);
                } catch (JsonProcessingException e) {
                    LOGGER.error("Error converting request to JSON", e);
                }
            } else if (request instanceof String) {
                logRequestContents((String) request);
            } else {
                LOGGER.warn("Unexpected request object type: {}", request.getClass().getName());
            }
        }
    }

    private void logRequestContents(String request) {
        LOGGER.info("REQUEST: {}", request);
    }

}
