package br.com.lockbox.api.repositories;

import br.com.lockbox.api.models.VaultEntity;
import br.com.lockbox.api.repositories.projections.VaultWithoutCategoryProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VaultRepository extends JpaRepository<VaultEntity, Long> {
  Page<VaultWithoutCategoryProjection> findAllBy(Pageable pageable);
}
