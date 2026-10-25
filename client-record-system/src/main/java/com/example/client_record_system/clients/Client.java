package com.example.client_record_system.clients;

import jakarta.validation.constraints.Pattern;

import java.time.OffsetDateTime;

public record Client (
    Long id,

    @Pattern(regexp = ".*\\S.*", message = "must not be blank")
    String fullName,

    @Pattern(regexp = "^[MF]$", message = "must be 'M' or 'F'")
    String gender,

    ClientStatus status,

    OffsetDateTime createDttm,

    OffsetDateTime modifyDttm
){

}
