package lv.javaguru.travel.insurance.dto.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import org.springframework.boot.jackson.JacksonComponent;
import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ValueSerializer;

@JacksonComponent
public class BigDecimalSerializer extends ValueSerializer<BigDecimal> {

    @Override
    public void serialize(BigDecimal originalValue, JsonGenerator gen, SerializationContext context)
            throws JacksonException {
        BigDecimal serializedValue = originalValue.setScale(2, RoundingMode.HALF_UP);
        gen.writeNumber(serializedValue .toPlainString());
    }

}
