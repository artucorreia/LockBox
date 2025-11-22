package br.com.lockbox.api.services.implementations;

import br.com.lockbox.api.models.UserEntity;
import br.com.lockbox.api.services.AuthenticatedUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class AuthenticatedUserServiceImpl implements AuthenticatedUserService {
  @Override
  public Optional<UserEntity> findCurrentUser() {
    log.info("Finding current authenticated user");
    return Optional.ofNullable(SecurityContextHolder.getContext())
        .map(SecurityContext::getAuthentication)
        .filter(Authentication::isAuthenticated)
        .map(Authentication::getPrincipal)
        .map(UserEntity.class::cast);
  }

  @Override
  public Optional<Long> findCurrentUserId() {
    log.info("Finding current authenticated user id");
    Optional<UserEntity> optionalUserEntity =
        Optional.ofNullable(SecurityContextHolder.getContext())
            .map(SecurityContext::getAuthentication)
            .filter(Authentication::isAuthenticated)
            .map(Authentication::getPrincipal)
            .map(UserEntity.class::cast);
    return optionalUserEntity.map(UserEntity::getId);
  }
}
