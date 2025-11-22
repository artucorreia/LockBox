package br.com.lockbox.api.services.implementations;

import br.com.lockbox.api.exceptions.LockBoxException;
import br.com.lockbox.api.mappers.CategoryMapper;
import br.com.lockbox.api.models.CategoryEntity;
import br.com.lockbox.api.repositories.CategoryRepository;
import br.com.lockbox.api.services.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
  private final CategoryRepository categoryRepository;
  private final CategoryMapper categoryMapper;

  @Override
  public CategoryEntity findById(Long id) {
    log.info("Finding a category by id: {}", id);
    return categoryRepository
        .findById(id)
        .orElseThrow(() -> new LockBoxException("no category found for the given id", HttpStatus.NOT_FOUND));
  }

  @Override
  public Optional<CategoryEntity> findByName(String name) {
    log.info("Finding a category by name: {}", name);
    return categoryRepository.findByNameIgnoreCase(name);
  }

  @Override
  public List<CategoryEntity> findAll() {
    log.info("Finding all categories");
    return categoryRepository.findAllBy().stream()
        .map(categoryMapper::projectionWithoutVaultsToEntity)
        .toList();
  }

  @Override
  public Page<CategoryEntity> findAll(Pageable pageable) {
    log.info("Finding all categories with pagination");
    return categoryRepository
        .findAllBy(pageable)
        .map(categoryMapper::projectionWithoutVaultsToEntity);
  }

  @Override
  public List<CategoryEntity> findAllWithVaults() {
    log.info("Finding all categories with vaults");
    return categoryRepository.findAll();
  }

  @Override
  public void insert(CategoryEntity category) {
    log.info("Inserting a new category");
    Optional<CategoryEntity> optionalCategoryEntity = findByName(category.getName().trim());
    if (optionalCategoryEntity.isPresent()) throw new LockBoxException("category already exists", HttpStatus.BAD_REQUEST);

    category.setName(category.getName().trim());
    categoryRepository.save(category);
  }

  @Override
  public void deleteById(Long id) {
    log.info("Deleting a category by id: {}", id);
    CategoryEntity categoryEntity = findById(id);
    categoryRepository.delete(categoryEntity);
  }
}
