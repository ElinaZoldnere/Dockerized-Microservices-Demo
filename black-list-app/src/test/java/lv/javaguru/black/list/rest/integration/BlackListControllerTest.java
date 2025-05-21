package lv.javaguru.black.list.rest.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static uk.org.webcompere.modelassert.json.JsonAssertions.assertJson;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class BlackListControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JsonFileReader reader;
    @Autowired
    private Gson gson;
    @Autowired
    private TestDataFileProvider fileProvider;

    private final String testCasePrefix = "Test_";

    @TestFactory
    Stream<DynamicTest> dynamicTestsFromStream() {
        return fileProvider.provideTestData()
                .map(data ->
                        DynamicTest.dynamicTest("Test Case: "
                                        + data.replace(testCasePrefix, ""),
                                () -> check((data)))
                );
    }

    private void check(String fileName) throws Exception {
        JsonObject testDataJson =
                gson.fromJson(reader.readJsonFromFile(fileName),
                        JsonObject.class);
        JsonObject requestJson = testDataJson.getAsJsonObject("request");
        JsonObject expectedResponseJson = testDataJson.getAsJsonObject("expectedResponse");

        String endpoint = "/blacklist/person/check/";
        MockHttpServletResponse calculatedResponse = mockMvc.perform(post(endpoint)
                        .content(requestJson.toString())
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        String expectedResponseAsString = expectedResponseJson.toString();
        String calculatedResponseAsString = calculatedResponse.getContentAsString(StandardCharsets.UTF_8);

        assertJson(calculatedResponseAsString)
                .where()
                .keysInAnyOrder()
                .arrayInAnyOrder()
                .isEqualTo(expectedResponseAsString);
    }

}
