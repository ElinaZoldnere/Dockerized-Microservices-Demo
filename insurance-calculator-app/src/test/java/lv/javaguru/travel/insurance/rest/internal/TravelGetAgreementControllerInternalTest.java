package lv.javaguru.travel.insurance.rest.internal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static uk.org.webcompere.modelassert.json.JsonAssertions.assertJson;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.nio.charset.StandardCharsets;
import lv.javaguru.travel.insurance.config.SharedIntegrationTest;
import lv.javaguru.travel.insurance.core.domain.entities.AgreementEntity;
import lv.javaguru.travel.insurance.rest.JsonFileReader;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@SharedIntegrationTest
class TravelGetAgreementControllerInternalTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JsonFileReader reader;
    @Autowired
    private Gson gson;
    @PersistenceContext
    private EntityManager entityManager;

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void controllerShouldFindExistingUuid() throws Exception {
        JsonObject testDataJson = gson.fromJson(reader.readJsonFromFile(
                        "internal/ControllerInternalTest_Combined_9.01_all_fields_are_present_and_valid.json"),
                JsonObject.class);
        JsonObject setUpRequestJson = testDataJson.getAsJsonObject("set-up request");
        JsonObject expectedResponseJson = testDataJson.getAsJsonObject("expectedResponse");

        mockMvc.perform(post("/insurance/travel/api/v2/")
                        .content(setUpRequestJson.toString())
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        AgreementEntity insertedAgreement = findLastInsertedAgreement();
        String insertedUuid = insertedAgreement.getUuid();

        expectedResponseJson.addProperty("uuid", insertedUuid);

        MockHttpServletResponse response = mockMvc.perform(get(
                        "/insurance/travel/api/internal/agreement/" + insertedUuid)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        String expectedResponseAsString = expectedResponseJson.toString();
        String actualResponseAsString = response.getContentAsString(StandardCharsets.UTF_8);

        assertJson(actualResponseAsString)
                .where()
                .keysInAnyOrder()
                .arrayInAnyOrder()
                .isEqualTo(expectedResponseAsString);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void controllerShouldReturnErrorForNotExistingUuid() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(get(
                        "/insurance/travel/api/internal/agreement/NOT-EXISTING-UUID")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        String expectedResponseAsString = reader.readJsonFromFile(
                "internal/ControllerInternalTest_Response_3.01_uuid_not_in_database.json");
        String actualResponseAsString = response.getContentAsString(StandardCharsets.UTF_8);

        assertJson(actualResponseAsString)
                .where()
                .keysInAnyOrder()
                .arrayInAnyOrder()
                .isEqualTo(expectedResponseAsString);
    }

    private AgreementEntity findLastInsertedAgreement() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<AgreementEntity> cq = cb.createQuery(AgreementEntity.class);
        Root<AgreementEntity> root = cq.from(AgreementEntity.class);
        cq.orderBy(cb.desc(root.get("id")));
        TypedQuery<AgreementEntity> query = entityManager.createQuery(cq);
        query.setMaxResults(1);
        return query.getSingleResult();
    }

}
