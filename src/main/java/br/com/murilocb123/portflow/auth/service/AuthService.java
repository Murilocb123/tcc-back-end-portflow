package br.com.murilocb123.portflow.auth.service;


import br.com.murilocb123.portflow.auth.dto.LoginRequest;
import br.com.murilocb123.portflow.auth.dto.RegisterRequest;
import br.com.murilocb123.portflow.auth.dto.ResponseAuth;

public interface AuthService {

    void requestCreateUser(RegisterRequest registerRequest);

    ResponseAuth login(LoginRequest loginRequest);
}
