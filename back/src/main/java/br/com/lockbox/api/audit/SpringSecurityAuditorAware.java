package br.com.lockbox.api.audit;

import br.com.lockbox.api.models.UserEntity;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class SpringSecurityAuditorAware implements AuditorAware<Long> {
  @Override
  public Optional<Long> getCurrentAuditor() {
    Optional<UserEntity> userEntity =
        Optional.ofNullable(SecurityContextHolder.getContext())
            .map(SecurityContext::getAuthentication)
            .filter(Authentication::isAuthenticated)
            .map(Authentication::getPrincipal)
            .map(UserEntity.class::cast);

    return userEntity.map(UserEntity::getId);
  }
}
