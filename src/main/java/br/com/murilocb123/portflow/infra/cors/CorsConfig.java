package br.com.murilocb123.portflow.infra.cors;

import br.com.murilocb123.portflow.service.UserService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class CorsConfig implements WebMvcConfigurer {

    private final UserService userService;

    @PersistenceContext
    private EntityManager entityManager;

    @Value("${allowed.origin1}")
    private String allowedOrigin1;

    @Value("${allowed.origin2}")
    private String allowedOrigin2;


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        log.info("Adding CORS mappings");
        log.info("Allowed Origin 1: {}", allowedOrigin1);
        log.info("Allowed Origin 2: {}", allowedOrigin2);
        registry.addMapping("/**")
                .allowedOrigins(allowedOrigin1, allowedOrigin2)
                .allowedMethods("GET", "POST", "DELETE", "PUT", "PATCH");
    }
}