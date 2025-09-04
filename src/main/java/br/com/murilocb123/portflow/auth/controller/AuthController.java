package br.com.murilocb123.portflow.auth.controller;

import br.com.murilocb123.portflow.auth.dto.LoginRequest;
import br.com.murilocb123.portflow.auth.dto.RegisterRequest;
import br.com.murilocb123.portflow.auth.dto.ResponseAuth;
import br.com.murilocb123.portflow.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {

    AuthService authService;

    @PostMapping("/login")
    public ResponseAuth login(@Valid @RequestBody LoginRequest body) {
        return authService.login(body);
    }


    @PostMapping("/register")
    public void register(@RequestBody @Valid RegisterRequest body) {
        authService.requestCreateUser(body);
    }
}
