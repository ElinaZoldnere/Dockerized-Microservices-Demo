package lv.javaguru.travel.insurance.core.blacklist;

import lombok.extern.slf4j.Slf4j;
import lv.javaguru.travel.insurance.core.blacklist.dto.BlackListCheckPersonRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;

@Component
@Profile({"mysql-container", "mysql-local"})
@Slf4j
public class BlackListWebClient {

    private final WebClient webClient;

    public BlackListWebClient(WebClient.Builder webClientBuilder,
                              @Value("${blacklist.api.url}") String blackListApiUrl) {
        // Use the Spring-provided WebClient.Builder to build a preconfigured WebClient
        this.webClient = webClientBuilder
                .baseUrl(blackListApiUrl)
                // for debugging
                /*.filter((clientRequest, next) -> {
                    logRequestHeaders(clientRequest);
                    return next.exchange(clientRequest).doOnSuccess(this::logResponseHeaders);
                })*/
                .build();
    }

    public String checkPerson(BlackListCheckPersonRequest request) throws Exception {
        log.info("Check person in BlackList: {}", request);
        String stringResponse = performCall(request);
        log.info("Received response from BlackList: {}", stringResponse);
        return stringResponse;
    }

    private String performCall(BlackListCheckPersonRequest request) {
        try {
            return webClient.post()
                    .uri("/check/")
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block(); //converts to synchronous call
        } catch (WebClientException e) {
            log.error("Error occurred while calling BlackList service: {}", e.getMessage());
            throw e;
        }
    }

    // for debugging
    /*private void logRequestHeaders(org.springframework.web.reactive.function.client.ClientRequest clientRequest) {
        log.info("Outgoing Request: {} {}", clientRequest.method(), clientRequest.url());
        clientRequest.headers().forEach((name, values) ->
                values.forEach(value -> log.info("Request Header: {} = {}", name, value))
        );
    }

    //for debugging
    private void logResponseHeaders(org.springframework.web.reactive.function.client.ClientResponse clientResponse) {
        log.info("Response Status: {}", clientResponse.statusCode());
        clientResponse.headers().asHttpHeaders().forEach((name, values) ->
                values.forEach(value -> log.info("Response Header: {} = {}", name, value))
        );
    }*/

}
