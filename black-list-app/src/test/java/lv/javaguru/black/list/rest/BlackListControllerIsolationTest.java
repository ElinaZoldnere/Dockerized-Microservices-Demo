package lv.javaguru.black.list.rest;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import lv.javaguru.black.list.core.api.command.BlackListedPersonCoreCommand;
import lv.javaguru.black.list.core.api.command.BlackListedPersonCoreResult;
import lv.javaguru.black.list.core.api.command.BlackListedPersonCoreResultSuccess;
import lv.javaguru.black.list.core.api.dto.PersonDTO;
import lv.javaguru.black.list.core.service.BlackListedPersonService;
import lv.javaguru.black.list.dto.BlackListedPersonCheckRequest;
import lv.javaguru.black.list.dto.BlackListedPersonCheckResponse;
import lv.javaguru.black.list.dto.BlackListedPersonCheckResponseSuccess;
import lv.javaguru.black.list.dto.DTOConverter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(BlackListController.class)
class BlackListControllerIsolationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private DTOConverter converter;
    @MockitoBean
    private BlackListedPersonService service;
    @MockitoBean
    private MessageLogger messageLogger;
    @MockitoBean
    private ExecutionTimeLogger executionTimeLogger;

    @Test
    void checkShouldReturnSuccessResponseWhenPersonIsBlackListed() throws Exception {
        String personFirstName = "Jānis";
        String personLastName = "Bērziņš";
        String personalCode = "123456-12345";

        BlackListedPersonCheckRequest request = new BlackListedPersonCheckRequest(
                personFirstName, personLastName, personalCode);
        PersonDTO person = new PersonDTO(personFirstName, personLastName, personalCode);

        BlackListedPersonCoreCommand command = new BlackListedPersonCoreCommand(person);
        BlackListedPersonCoreResult result = new BlackListedPersonCoreResultSuccess(person, true);

        BlackListedPersonCheckResponse expectedResponse = new BlackListedPersonCheckResponseSuccess(
                personFirstName, personLastName, personalCode, true);

        when(converter.buildCoreCommand(request)).thenReturn(command);
        when(service.checkPerson(command)).thenReturn(result);
        when(converter.buildResponse(result)).thenReturn(expectedResponse);

        mockMvc.perform(post("/blacklist/person/check/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.personFirstName").value(personFirstName))
                .andExpect(jsonPath("$.personLastName").value(personLastName))
                .andExpect(jsonPath("$.personalCode").value(personalCode))
                .andExpect(jsonPath("$.blackListed").value(true));

        verify(converter).buildCoreCommand(request);
        verify(service).checkPerson(command);
        verify(converter).buildResponse(result);

        verify(messageLogger).log("REQUEST", request);
        verify(messageLogger).log("RESPONSE", expectedResponse);
        verify(executionTimeLogger).log(anyLong());
    }

}
