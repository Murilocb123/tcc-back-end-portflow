package br.com.murilocb123.portflow.auth.dto;

import java.util.UUID;

public record ResponseAuth(String name, String token, UUID tenant) { }