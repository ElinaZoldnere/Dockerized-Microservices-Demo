package lv.javaguru.travel.insurance.core.api.dto;

import java.time.LocalDateTime;

public record AckMessageDTO(
        String uuid,

        String path,

        LocalDateTime timestamp) {
}
