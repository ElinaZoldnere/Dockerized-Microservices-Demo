package lv.javaguru.travel.insurance.web.v1;

import lombok.RequiredArgsConstructor;
import lv.javaguru.travel.insurance.core.api.command.TravelCalculatePremiumCoreCommand;
import lv.javaguru.travel.insurance.core.api.command.TravelCalculatePremiumCoreResult;
import lv.javaguru.travel.insurance.core.services.TravelCalculatePremiumService;
import lv.javaguru.travel.insurance.dto.v1.DtoV1Converter;
import lv.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;
import lv.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumResponseV1;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class TravelInsuranceControllerV1 {

    private static final String PATH_TRAVEL_INSURANCE_WEB_V1 = "/insurance/travel/web/v1";
    private static final String VIEW_TRAVEL_CALCULATE_PREMIUM_V1 = "travel-calculate-premium-v1";

    private final TravelCalculatePremiumService service;
    private final DtoV1Converter dtoV1Converter;

    @GetMapping(PATH_TRAVEL_INSURANCE_WEB_V1)
    public String showForm(ModelMap modelMap) {
        modelMap.addAttribute("request", new TravelCalculatePremiumRequestV1());
        return VIEW_TRAVEL_CALCULATE_PREMIUM_V1;
    }

    @PostMapping(PATH_TRAVEL_INSURANCE_WEB_V1)
    public String processForm(@ModelAttribute(value = "request") TravelCalculatePremiumRequestV1 request,
                              ModelMap modelMap) {
        TravelCalculatePremiumCoreCommand coreCommand = dtoV1Converter.buildCoreCommand(request);
        TravelCalculatePremiumCoreResult coreResult = service.calculatePremium(coreCommand);
        TravelCalculatePremiumResponseV1 response = dtoV1Converter.buildResponse(coreResult);
        modelMap.addAttribute("response", response);
        return VIEW_TRAVEL_CALCULATE_PREMIUM_V1;
    }

}
