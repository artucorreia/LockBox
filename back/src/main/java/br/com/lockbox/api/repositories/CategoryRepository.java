package br.com.lockbox.api.repositories;

import br.com.lockbox.api.models.CategoryEntity;
import br.com.lockbox.api.repositories.projections.CategoryWithoutVaultsProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
  Optional<CategoryEntity> findByNameIgnoreCase(String name);

  List<CategoryWithoutVaultsProjection> findAllBy();

  Page<CategoryWithoutVaultsProjection> findAllBy(Pageable pageable);
}
