package lv.javaguru.black.list.rest.integration;

import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(access = lombok.AccessLevel.PACKAGE)
public class TestDataFileProvider {

    private final ResourcePatternResolver resourcePatternResolver;

    public Stream<String> provideTestData() {
        String directoryPattern = "classpath:rest/*.json";
        try {
            Resource[] resources = resourcePatternResolver.getResources(directoryPattern);
            return Stream.of(resources)
                    .map(Resource::getFilename);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

}
