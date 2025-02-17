package lv.javaguru.travel.insurance.web.v2.support;

import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lv.javaguru.travel.insurance.core.domain.ClassifierValue;
import lv.javaguru.travel.insurance.core.repositories.ClassifierValueRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class WebDropdownMenuService {

    private final ClassifierValueRepository repository;

    List<String> getClassifiers(String classifierTitle) {
        List<ClassifierValue> values = repository.findByClassifier_Title(classifierTitle);
        return values.stream()
                .map(ClassifierValue::getIc)
                .toList();
    }

}
