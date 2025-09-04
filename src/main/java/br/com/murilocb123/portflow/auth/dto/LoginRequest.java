package br.com.murilocb123.portflow.auth.dto;

import jakarta.validation.constraints.NotNull;

public record LoginRequest(@NotNull String email, @NotNull String password){}