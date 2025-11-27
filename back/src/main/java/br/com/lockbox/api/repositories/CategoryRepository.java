package br.com.lockbox.api.repositories;

import br.com.lockbox.api.models.CategoryEntity;
import br.com.lockbox.api.repositories.projections.CategoryWithoutVaultsProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
  Optional<CategoryEntity> findByIdAndDeletedFalseAndUserId(Long id, Long userId);

  Optional<CategoryEntity> findByNameIgnoreCaseAndUserIdAndDeletedFalse(String name, Long userId);

  List<CategoryEntity> findByUserIdAndDeletedFalse(Long userId);

  List<CategoryWithoutVaultsProjection> findAllByUserIdAndDeletedFalse(Long userId);

  Page<CategoryWithoutVaultsProjection> findAllByUserIdAndDeletedFalse(
      Long userId, Pageable pageable);
}
