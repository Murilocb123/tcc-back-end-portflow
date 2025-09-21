package br.com.murilocb123.portflow.infra.security;

import br.com.murilocb123.portflow.repositories.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SecurityFilter extends OncePerRequestFilter {
    TokenService tokenService;
    UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = this.recoverToken(request);
        var login = tokenService.validateToken(token);

        if (login != null) {
            var user = userRepository.findByEmail(login).orElseThrow(() -> new RuntimeException("UserEntity Not Found"));
            var authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
            var authentication = new UsernamePasswordAuthenticationToken(user, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            AppContextHolder.setUserId(user.getId());
            AppContextHolder.setTenant(user.getTenantId());
            AppContextHolder.setCurrentPortfolio(recoverPortfolioId(request));
        }
        try {
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            AppContextHolder.clear();
        }
    }

    private UUID recoverPortfolioId(HttpServletRequest request) {
        var portfolioIdHeader = request.getHeader("selectedPortfolio");
        if (portfolioIdHeader == null) return null;
        return UUID.fromString(portfolioIdHeader);
    }

    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null) return null;
        return authHeader.replace("Bearer ", "");
    }
}