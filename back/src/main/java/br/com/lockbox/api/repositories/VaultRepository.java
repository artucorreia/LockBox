package br.com.lockbox.api.repositories;

import br.com.lockbox.api.models.VaultEntity;
import br.com.lockbox.api.repositories.projections.VaultWithoutCategoryProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VaultRepository extends JpaRepository<VaultEntity, Long> {
  Optional<VaultEntity> findByIdAndDeletedFalse(Long id);

  List<VaultEntity> findByDeletedFalse();

  Page<VaultWithoutCategoryProjection> findAllByAndDeletedFalse(Pageable pageable);
}
