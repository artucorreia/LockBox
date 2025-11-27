package br.com.lockbox.api.services.implementations;

import br.com.lockbox.api.exceptions.LockBoxException;
import br.com.lockbox.api.mappers.CategoryMapper;
import br.com.lockbox.api.models.CategoryEntity;
import br.com.lockbox.api.models.UserEntity;
import br.com.lockbox.api.repositories.CategoryRepository;
import br.com.lockbox.api.services.AuthenticatedUserService;
import br.com.lockbox.api.services.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
  private final CategoryRepository categoryRepository;
  private final AuthenticatedUserService authenticatedUserService;
  private final CategoryMapper categoryMapper;

  @Override
  public CategoryEntity findById(Long id) {
    log.info("Finding a category by id: {}", id);
    Long authenticatedUserId = findAuthenticatedUserId();
    return categoryRepository
        .findByIdAndDeletedFalseAndUserId(id, authenticatedUserId)
        .orElseThrow(
            () -> new LockBoxException("no category found for the given id", HttpStatus.NOT_FOUND));
  }

  @Override
  public Optional<CategoryEntity> findByName(String name) {
    log.info("Finding a category by name: {}", name);
    Long authenticatedUserId = findAuthenticatedUserId();
    return categoryRepository.findByNameIgnoreCaseAndUserIdAndDeletedFalse(name, authenticatedUserId);
  }

  @Override
  public List<CategoryEntity> findAll() {
    log.info("Finding all categories");
    Long authenticatedUserId = findAuthenticatedUserId();
    return categoryRepository.findAllByUserIdAndDeletedFalse(authenticatedUserId).stream()
        .map(categoryMapper::projectionWithoutVaultsToEntity)
        .toList();
  }

  @Override
  public Page<CategoryEntity> findAll(Pageable pageable) {
    log.info("Finding all categories with pagination");
    Long authenticatedUserId = findAuthenticatedUserId();
    return categoryRepository
        .findAllByUserIdAndDeletedFalse(authenticatedUserId, pageable)
        .map(categoryMapper::projectionWithoutVaultsToEntity);
  }

  @Override
  public List<CategoryEntity> findAllWithVaults() {
    log.info("Finding all categories with vaults");
    Long authenticatedUserId = findAuthenticatedUserId();
    return categoryRepository.findByUserIdAndDeletedFalse(authenticatedUserId);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void insert(CategoryEntity category) {
    log.info("Inserting a new category");
    Optional<CategoryEntity> optionalCategoryEntity = findByName(category.getName().trim());
    if (optionalCategoryEntity.isPresent())
      throw new LockBoxException("category already exists", HttpStatus.BAD_REQUEST);

    UserEntity authenticatedUser = findAuthenticatedUser();
    category.setName(category.getName().trim());
    category.setUser(authenticatedUser);
    category.setDeleted(false);
    categoryRepository.save(category);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void deleteById(Long id) {
    log.info("Deleting a category by id: {}", id);
    CategoryEntity categoryEntity = findById(id);
    if (!categoryEntity.getVaults().isEmpty())
      throw new LockBoxException(
          "It is not possible to delete categories that have linked vaults",
          HttpStatus.BAD_REQUEST);
    categoryEntity.setDeleted(true);
    categoryRepository.save(categoryEntity);
  }

  private UserEntity findAuthenticatedUser() {
    return authenticatedUserService
        .findCurrentUser()
        .orElseThrow(
            () ->
                new LockBoxException(
                    "An error occurred while retrieving the logged-in user",
                    HttpStatus.INTERNAL_SERVER_ERROR));
  }

  private Long findAuthenticatedUserId() {
    return authenticatedUserService
        .findCurrentUserId()
        .orElseThrow(
            () ->
                new LockBoxException(
                    "An error occurred while retrieving the logged-in user",
                    HttpStatus.INTERNAL_SERVER_ERROR));
  }
}
