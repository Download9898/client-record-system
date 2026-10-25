package com.example.client_record_system.web;

import java.time.OffsetDateTime;

public record ErrorResponseDto(
        int status,
        String message,
        OffsetDateTime errorTime

) {
}
