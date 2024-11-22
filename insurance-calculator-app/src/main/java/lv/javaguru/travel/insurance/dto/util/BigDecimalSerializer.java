package lv.javaguru.travel.insurance.dto.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.springframework.boot.jackson.JsonComponent;

@JsonComponent
public class BigDecimalSerializer extends JsonSerializer<BigDecimal> {

    @Override
    public void serialize(BigDecimal originalValue, JsonGenerator gen, SerializerProvider serializers)
            throws IOException {
        BigDecimal serializedValue = originalValue.setScale(2, RoundingMode.HALF_UP);
        gen.writeNumber(serializedValue .toPlainString());
    }

}
