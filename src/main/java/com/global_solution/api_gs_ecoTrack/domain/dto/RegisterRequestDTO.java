package com.global_solution.api_gs_ecoTrack.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record RegisterRequestDTO(
        @NotBlank(message = "Nome é obrigatório")
        String name,
        @NotNull(message = "Data de aniversário é obrigatória")
        LocalDate birthDate,
        @NotBlank(message = "A sigla do estado é obrigatória")
        String abbreviationState,
        @NotBlank(message = "Email é obrigatório")
        @Email(message = "Email inválido")
        String email,
        @NotBlank(message = "Senha é obrigatória")
        String password) {
}
