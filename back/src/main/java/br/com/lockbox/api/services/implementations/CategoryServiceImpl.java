package br.com.lockbox.api.services.implementations;

import br.com.lockbox.api.exceptions.LockBoxException;
import br.com.lockbox.api.mappers.CategoryMapper;
import br.com.lockbox.api.models.CategoryEntity;
import br.com.lockbox.api.repositories.CategoryRepository;
import br.com.lockbox.api.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
  private final Logger LOGGER = Logger.getLogger(CategoryServiceImpl.class.getName());
  private final CategoryRepository categoryRepository;
  private final CategoryMapper categoryMapper;

  @Override
  public CategoryEntity findById(Long id) {
    LOGGER.info("Finding a category by id");
    return categoryRepository
        .findById(id)
        .orElseThrow(() -> new LockBoxException("no category found for the given id", HttpStatus.NOT_FOUND));
  }

  @Override
  public Optional<CategoryEntity> findByName(String name) {
    LOGGER.info("Finding a category by name");
    return categoryRepository.findByNameIgnoreCase(name);
  }

  @Override
  public List<CategoryEntity> findAll() {
    LOGGER.info("Finding all categories");
    return categoryRepository.findAllBy().stream()
        .map(categoryMapper::projectionWithoutVaultsToEntity)
        .toList();
  }

  @Override
  public Page<CategoryEntity> findAll(Pageable pageable) {
    LOGGER.info("Finding all categories with pagination");
    return categoryRepository
        .findAllBy(pageable)
        .map(categoryMapper::projectionWithoutVaultsToEntity);
  }

  @Override
  public List<CategoryEntity> findAllWithVaults() {
    LOGGER.info("Finding all categories with vaults");
    return categoryRepository.findAll();
  }

  @Override
  public void insert(CategoryEntity category) {
    LOGGER.info("Inserting a new category");
    Optional<CategoryEntity> optionalCategoryEntity = findByName(category.getName().trim());
    if (optionalCategoryEntity.isPresent()) throw new LockBoxException("category already exists", HttpStatus.BAD_REQUEST);

    category.setName(category.getName().trim());
    categoryRepository.save(category);
  }

  @Override
  public void deleteById(Long id) {
    LOGGER.info("Deleting a category by id");
    CategoryEntity categoryEntity = findById(id);
    categoryRepository.delete(categoryEntity);
  }
}
