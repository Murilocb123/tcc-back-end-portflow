package br.com.murilocb123.portflow.infra.jpa;

import br.com.murilocb123.portflow.infra.security.AppContextHolder;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;


@Component
@NoArgsConstructor
public class AuditorAwareImpl implements AuditorAware<UUID> {

    @Override
    public Optional<UUID> getCurrentAuditor() {
        var userId = AppContextHolder.getUserId();
        if (userId == null) {
            return Optional.empty();
        }
        return Optional.of(userId); // retorna o userid
    }
}
