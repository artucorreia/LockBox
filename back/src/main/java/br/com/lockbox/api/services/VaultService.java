package br.com.lockbox.api.services;

import br.com.lockbox.api.models.VaultEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface VaultService {
  VaultEntity findById(Long id);

  List<VaultEntity> findAll();

  Page<VaultEntity> findAll(Pageable pageable);

  void update(Long id, VaultEntity updatedVault);

  void insert(VaultEntity newVault);

  void deleteById(Long id);
}
