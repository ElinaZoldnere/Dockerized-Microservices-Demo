package lv.javaguru.travel.insurance.core.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import org.springframework.stereotype.Component;

@Component
public class DateHelper {

    public LocalDate newDate(String dateStr) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
            return LocalDate.parse(dateStr, formatter);
        } catch (DateTimeParseException e) {
            throw new RuntimeException(e);
        }
    }

}
