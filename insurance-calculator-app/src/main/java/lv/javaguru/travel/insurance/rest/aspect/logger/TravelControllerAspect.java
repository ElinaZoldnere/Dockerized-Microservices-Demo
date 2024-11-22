package lv.javaguru.travel.insurance.rest.aspect.logger;

import com.google.common.base.Stopwatch;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class TravelControllerAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(TravelControllerAspect.class);

    private final ControllerLogRequest logRequest;
    private final ControllerLogResponse logResponse;

    @Pointcut("execution(* lv.javaguru.travel.insurance.rest.*.*Controller*.calculatePremium(..)) || "
            + "execution(* lv.javaguru.travel.insurance.rest.internal.*Controller*.getAgreement(..))")
    public void controllerMethods() {
    }

    @Around("controllerMethods()")
    public Object logControllerMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        Stopwatch stopwatch = Stopwatch.createStarted();

        Object response = joinPoint.proceed();

        stopwatch.stop();
        long executionTime = stopwatch.elapsed().toMillis();

        logRequest.log(joinPoint);
        logResponse.log(response);
        ControllerLogExecutionTime.log(executionTime);

        return response;
    }

}
