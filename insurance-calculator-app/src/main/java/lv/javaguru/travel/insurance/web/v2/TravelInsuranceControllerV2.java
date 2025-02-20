package lv.javaguru.travel.insurance.web.v2;

import lombok.RequiredArgsConstructor;
import lv.javaguru.travel.insurance.core.api.command.TravelCalculatePremiumCoreCommand;
import lv.javaguru.travel.insurance.core.api.command.TravelCalculatePremiumCoreResult;
import lv.javaguru.travel.insurance.core.services.TravelCalculatePremiumService;
import lv.javaguru.travel.insurance.dto.v2.DtoV2Converter;
import lv.javaguru.travel.insurance.dto.v2.TravelCalculatePremiumRequestV2;
import lv.javaguru.travel.insurance.dto.v2.TravelCalculatePremiumResponseV2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor(access = lombok.AccessLevel.PACKAGE)
public class TravelInsuranceControllerV2 {

    public static final String PATH_TRAVEL_INSURANCE_WEB_V2 = "/insurance/travel/web/v2";
    private static final String VIEW_TRAVEL_CALCULATE_PREMIUM_V2 = "travel-calculate-premium-v2";

    private final TravelCalculatePremiumService service;
    private final DtoV2Converter dtoV2Converter;

    @GetMapping(PATH_TRAVEL_INSURANCE_WEB_V2)
    public String showForm(ModelMap modelMap) {
        modelMap.addAttribute("request", new TravelCalculatePremiumRequestV2());
        return VIEW_TRAVEL_CALCULATE_PREMIUM_V2;
    }

    @PostMapping(PATH_TRAVEL_INSURANCE_WEB_V2)
    public String processForm(@ModelAttribute(value = "request") TravelCalculatePremiumRequestV2 request,
                              ModelMap modelMap) {
        TravelCalculatePremiumCoreCommand coreCommand = dtoV2Converter.buildCoreCommand(request);
        TravelCalculatePremiumCoreResult coreResult = service.calculatePremium(coreCommand);
        TravelCalculatePremiumResponseV2 response = dtoV2Converter.buildResponse(coreResult);
        modelMap.addAttribute("response", response);
        return VIEW_TRAVEL_CALCULATE_PREMIUM_V2;
    }

}
