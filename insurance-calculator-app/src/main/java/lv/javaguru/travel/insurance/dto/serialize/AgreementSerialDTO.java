package lv.javaguru.travel.insurance.dto.serialize;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonRootName;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tools.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonRootName("agreement")
public class AgreementSerialDTO {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate agreementDateFrom;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate agreementDateTo;

    private String country;

    @JacksonXmlElementWrapper(useWrapping = false)
    private List<String> selectedRisks;

    @JacksonXmlElementWrapper(useWrapping = false)
    private List<PersonSerialDTO> persons;

    private BigDecimal agreementPremium;

    private String uuid;

}
