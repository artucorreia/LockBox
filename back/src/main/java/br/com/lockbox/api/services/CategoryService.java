package br.com.lockbox.api.services;

import br.com.lockbox.api.models.CategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
  CategoryEntity findById(Long id);

  Optional<CategoryEntity> findByName(String name);

  List<CategoryEntity> findAll();

  Page<CategoryEntity> findAll(Pageable pageable);

  List<CategoryEntity> findAllWithVaults();

  void insert(CategoryEntity category);

  void deleteById(Long id);
}
