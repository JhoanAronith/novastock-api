package com.jhoan.novastock.dto.response;

public record UserAuthResponseDTO(
        String token,
        String username,
        String message
) { }
