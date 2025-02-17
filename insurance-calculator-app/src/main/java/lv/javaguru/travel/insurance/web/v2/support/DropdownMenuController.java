package lv.javaguru.travel.insurance.web.v2.support;

import lombok.RequiredArgsConstructor;
import lv.javaguru.travel.insurance.web.v2.support.dto.DropdownMenuResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/insurance/travel/web/v2/dropdown")
@RequiredArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class DropdownMenuController {

    private final WebDropdownMenuService service;

    @GetMapping(path = "/countries")
    public DropdownMenuResponse countries() {
        return new DropdownMenuResponse(service.getClassifiers("COUNTRY"));
    }

}
