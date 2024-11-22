package lv.javaguru.black.list.rest;

import com.google.common.base.Stopwatch;
import lombok.RequiredArgsConstructor;
import lv.javaguru.black.list.core.api.command.BlackListedPersonCoreCommand;
import lv.javaguru.black.list.core.api.command.BlackListedPersonCoreResult;
import lv.javaguru.black.list.core.service.BlackListedPersonService;
import lv.javaguru.black.list.dto.BlackListedPersonCheckRequest;
import lv.javaguru.black.list.dto.BlackListedPersonCheckResponse;
import lv.javaguru.black.list.dto.DTOConverter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor (access = lombok.AccessLevel.PACKAGE)
@RequestMapping("/blacklist/person/check")
public class BlackListController {

    private final DTOConverter converter;
    private final BlackListedPersonService service;
    private final MessageLogger messageLogger;
    private final ExecutionTimeLogger executionTimeLogger;

    @PostMapping(path = "/",
            consumes = "application/json",
            produces = "application/json")
    public BlackListedPersonCheckResponse check(@RequestBody BlackListedPersonCheckRequest request) {
        messageLogger.log("REQUEST", request);
        Stopwatch stopwatch = Stopwatch.createStarted();

        BlackListedPersonCoreCommand command = converter.buildCoreCommand(request);
        BlackListedPersonCoreResult result = service.checkPerson(command);
        BlackListedPersonCheckResponse response = converter.buildResponse(result);

        executionTimeLogger.log(stopwatch.stop().elapsed().toMillis());
        messageLogger.log("RESPONSE", response);
        return response;
    }

}
