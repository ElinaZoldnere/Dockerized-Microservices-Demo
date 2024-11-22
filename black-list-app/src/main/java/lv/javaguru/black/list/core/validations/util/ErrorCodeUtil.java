package lv.javaguru.black.list.core.validations.util;

import java.io.IOException;
import java.util.Properties;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Component;

@Component
public class ErrorCodeUtil {

    private final Properties props;

    ErrorCodeUtil() {
        Resource resource = new ClassPathResource("errorCodes.properties");
        try {
            props = PropertiesLoaderUtils.loadProperties(resource);
        } catch (IOException e) {
            throw new IllegalStateException("Could not load error codes", e);
        }
    }

    public String getErrorDescription(String errorCode) {
        return props.getProperty(errorCode);
    }

}
