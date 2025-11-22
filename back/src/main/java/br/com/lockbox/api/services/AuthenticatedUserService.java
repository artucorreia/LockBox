package br.com.lockbox.api.services;

import br.com.lockbox.api.models.UserEntity;

import java.util.Optional;

public interface AuthenticatedUserService {
  Optional<UserEntity> findCurrentUser();

  Optional<Long> findCurrentUserId();
}
