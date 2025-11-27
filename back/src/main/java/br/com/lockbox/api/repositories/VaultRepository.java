package br.com.lockbox.api.repositories;

import br.com.lockbox.api.models.VaultEntity;
import br.com.lockbox.api.repositories.projections.VaultWithoutCategoryProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VaultRepository extends JpaRepository<VaultEntity, Long> {
  Optional<VaultEntity> findByIdAndDeletedFalseAndUserId(Long id, Long userId);

  List<VaultEntity> findByUserIdAndDeletedFalse(Long userId);

  Page<VaultWithoutCategoryProjection> findAllByUserIdAndDeletedFalse(
      Long userId, Pageable pageable);
}
