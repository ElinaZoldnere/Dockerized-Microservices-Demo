package lv.javaguru.travel.insurance.rest.security;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Base64;
import java.util.stream.Stream;
import lv.javaguru.travel.insurance.config.SharedIntegrationTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SharedIntegrationTest
class ControllerInternalAuthenticationAuthorizationTest {

    @Autowired
    private MockMvc mockMvc;

    @ParameterizedTest(name = "{0}")
    @MethodSource("validCredentialsProvider")
    void restControllerInternalShouldReturn200ForValidUserCredentials(
            String testName, String username, String password)
            throws Exception {
        String uuid = "UUID";
        String basicAuthHeader = "Basic " + Base64.getEncoder().encodeToString((username + ":" + password).getBytes());

        mockMvc.perform(get(
                        "/insurance/travel/api/internal/agreement/" + uuid)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .header(HttpHeaders.AUTHORIZATION, basicAuthHeader))
                .andExpect(status().isOk());
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("invalidCredentialsProvider")
    void restControllerInternalShouldReturn401ForWrongUserCredentials(
            String testName, String username, String password)
            throws Exception {
        String uuid = "UUID";
        String basicAuthHeader = "Basic " + Base64.getEncoder().encodeToString((username + ":" + password).getBytes());

        mockMvc.perform(get(
                        "/insurance/travel/api/internal/agreement/" + uuid)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .header(HttpHeaders.AUTHORIZATION, basicAuthHeader))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void restControllerInternalShouldReturn403ForUserWithNoPermissions() throws Exception {
        String username = "external_test_user";
        String password = "javaguru5";
        String uuid = "UUID";

        String basicAuthHeader = "Basic " + Base64.getEncoder().encodeToString((username + ":" + password).getBytes());

        mockMvc.perform(get(
                        "/insurance/travel/api/internal/agreement/" + uuid)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .header(HttpHeaders.AUTHORIZATION, basicAuthHeader))
                .andExpect(status().isForbidden());
    }

    @Test
    void restControllerInternalShouldReturn401ForIncorrectHeader() throws Exception {
        String uuid = "UUID";

        String incorrectAuthHeader = "Incorrect Authentication header";

        mockMvc.perform(get(
                        "/insurance/travel/api/internal/agreement/" + uuid)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .header(HttpHeaders.AUTHORIZATION, incorrectAuthHeader))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void restControllerInternalShouldReturn401ForNoCredentials() throws Exception {
        String uuid = "UUID";

        mockMvc.perform(get(
                        "/insurance/travel/api/internal/agreement/" + uuid)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isUnauthorized());
    }

    private static Stream<Arguments> validCredentialsProvider() {
        return Stream.of(
                Arguments.of("valid internal user", "internal_test_user", "javaguru4"),
                Arguments.of("valid admin", "admin", "javaguru3")
        );
    }

    private static Stream<Arguments> invalidCredentialsProvider() {
        return Stream.of(
                Arguments.of("invalid user name", "not_valid_username", "javaguru4"),
                Arguments.of("invalid password", "internal_test_user", "notValidPassword")
        );
    }

}
