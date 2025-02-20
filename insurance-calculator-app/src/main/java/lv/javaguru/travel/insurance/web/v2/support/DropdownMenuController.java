package lv.javaguru.travel.insurance.web.v2.support;

import lombok.RequiredArgsConstructor;
import lv.javaguru.travel.insurance.web.v2.support.dto.DropdownMenuResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static lv.javaguru.travel.insurance.web.v2.TravelInsuranceControllerV2.PATH_TRAVEL_INSURANCE_WEB_V2;

@RestController
@RequestMapping(PATH_TRAVEL_INSURANCE_WEB_V2 + "/dropdown")
@RequiredArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class DropdownMenuController {

    private final WebDropdownMenuService service;

    @GetMapping(path = "/countries", produces = "application/json")
    public DropdownMenuResponse countries() {
        return new DropdownMenuResponse(service.getClassifiers("COUNTRY"));
    }

    @GetMapping(path = "/medical-risk-limit-level", produces = "application/json")
    public DropdownMenuResponse medicalRiskLimitLevel() {
        return new DropdownMenuResponse(
                service.getClassifiers("MEDICAL_RISK_LIMIT_LEVEL"));
    }

}
