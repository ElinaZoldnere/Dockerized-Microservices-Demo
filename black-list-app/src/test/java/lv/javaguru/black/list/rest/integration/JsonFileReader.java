package lv.javaguru.black.list.rest.integration;

import java.io.File;
import java.nio.file.Files;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

@Component
public class JsonFileReader {

    public String readJsonFromFile(String filePath) {
        try {
            File file = ResourceUtils.getFile("classpath:rest/" + filePath);
            return new String(Files.readAllBytes(file.toPath()));
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

}
