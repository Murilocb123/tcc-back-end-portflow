package br.com.murilocb123.portflow.auth.service.impl;

import br.com.murilocb123.portflow.auth.dto.LoginRequest;
import br.com.murilocb123.portflow.auth.dto.RegisterRequest;
import br.com.murilocb123.portflow.auth.dto.ResponseAuth;
import br.com.murilocb123.portflow.auth.service.AuthService;
import br.com.murilocb123.portflow.domain.entities.TenantEntity;
import br.com.murilocb123.portflow.domain.entities.UserEntity;
import br.com.murilocb123.portflow.infra.exceptions.custom.BusinessException;
import br.com.murilocb123.portflow.infra.jpa.DatabaseSessionManager;
import br.com.murilocb123.portflow.infra.security.AppContextHolder;
import br.com.murilocb123.portflow.infra.security.TokenService;
import br.com.murilocb123.portflow.repositories.TenantRepository;
import br.com.murilocb123.portflow.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class AuthServiceImpl implements AuthService {
    UserRepository repository;
    TenantRepository tenantRepository;
    PasswordEncoder passwordEncoder;
    TokenService tokenService;
    DatabaseSessionManager databaseSessionManager;

    @Override
    public void requestCreateUser(RegisterRequest registerRequest) {
        UserEntity newUser = new UserEntity();
        newUser.setPassword(passwordEncoder.encode(registerRequest.password()));
        if (this.repository.findByEmail(registerRequest.email()).isPresent()) {
            throw new BusinessException("Email já cadastrado");
        }
        newUser.setEmail(registerRequest.email());
        newUser.setName(registerRequest.name());
        UUID tenantId = createTenant(registerRequest.email());
        databaseSessionManager.unbindSession();
        AppContextHolder.setTenant(tenantId);
        databaseSessionManager.bindSession();
        newUser.setTenantId(tenantId);
        repository.save(newUser);
    }


    public UUID createTenant(String email) {
        var tenant = new TenantEntity();
        tenant.setName(email);
        return tenantRepository.saveAndFlush(tenant).getId();
    }

    @Override
    public ResponseAuth login(LoginRequest loginRequest) {
        UserEntity user = this.repository.findByEmail(loginRequest.email()).orElseThrow(() -> new BusinessException("Usuario ou senha inválidos"));
        if(passwordEncoder.matches(loginRequest.password(), user.getPassword())) {
            String token = this.tokenService.generateToken(user);
            return new ResponseAuth(user.getName(), token, user.getTenantId());
        }
        throw new BusinessException("Usuario ou senha inválidos");
    }
}
