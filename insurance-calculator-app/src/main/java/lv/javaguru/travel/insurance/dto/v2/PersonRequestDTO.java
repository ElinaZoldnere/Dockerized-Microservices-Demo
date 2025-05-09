package lv.javaguru.travel.insurance.dto.v2;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonRequestDTO {

    private String personFirstName;

    private String personLastName;

    private String personalCode;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate personBirthDate;

    private String medicalRiskLimitLevel;

    private BigDecimal travelCost;

}
