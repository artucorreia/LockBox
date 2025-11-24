package br.com.lockbox.api.services;

import br.com.lockbox.api.models.RoleEntity;

import java.util.List;

public interface RoleService {
  List<RoleEntity> findByName(List<String> name);
}
