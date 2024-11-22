package lv.javaguru.doc.generator.core.api.dto;

import java.time.LocalDateTime;

public record AckMessageDTO(
        String uuid,

        String path,

        LocalDateTime timestamp) {
}
