package br.com.lockbox.api.services;

import br.com.lockbox.api.models.UserEntity;

public interface UserService {
  UserEntity findById(Long id);

  UserEntity findByEmail(String email);

  void insert(UserEntity newUser);
}
