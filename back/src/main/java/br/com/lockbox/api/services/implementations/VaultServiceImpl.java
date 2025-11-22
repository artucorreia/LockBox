package br.com.lockbox.api.services.implementations;

import br.com.lockbox.api.exceptions.LockBoxException;
import br.com.lockbox.api.mappers.VaultMapper;
import br.com.lockbox.api.models.CategoryEntity;
import br.com.lockbox.api.models.UserEntity;
import br.com.lockbox.api.models.VaultEntity;
import br.com.lockbox.api.repositories.VaultRepository;
import br.com.lockbox.api.services.AuthenticatedUserService;
import br.com.lockbox.api.services.CategoryService;
import br.com.lockbox.api.services.VaultService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class VaultServiceImpl implements VaultService {
  private final VaultRepository vaultRepository;
  private final CategoryService categoryService;
  private final AuthenticatedUserService authenticatedUserService;
  private final VaultMapper vaultMapper;

  @Override
  public VaultEntity findById(Long id) {
    log.info("Finding a vault by id: {}", id);
    return vaultRepository
        .findById(id)
        .orElseThrow(
            () -> new LockBoxException("no vault found for the given id", HttpStatus.NOT_FOUND));
  }

  @Override
  public List<VaultEntity> findAll() {
    log.info("Finding all vaults");
    return vaultRepository.findAll();
  }

  @Override
  public Page<VaultEntity> findAll(Pageable pageable) {
    log.info("Finding all vaults with pagination");
    return vaultRepository.findAllBy(pageable).map(vaultMapper::withoutCategoryProjectionToEntity);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void update(Long id, VaultEntity updatedVault) {
    log.info("Updating a vault by id: {}", id);
    VaultEntity vaultEntity = findById(id);

    boolean categoryChanged =
        updatedVault.getCategory().getId() != null
            && !updatedVault.getCategory().getId().equals(vaultEntity.getCategory().getId());
    boolean urlChanged =
        updatedVault.getUrl() != null && !vaultEntity.getUrl().equals(updatedVault.getUrl());
    boolean usernameChanged =
        updatedVault.getUsername() != null
            && !vaultEntity.getUsername().equals(updatedVault.getUsername());
    boolean passwordChanged =
        updatedVault.getPassword() != null
            && !vaultEntity.getPassword().equals(updatedVault.getPassword());
    if (urlChanged) {
      boolean containsHttp =
          updatedVault.getUrl().contains("http://") || updatedVault.getUrl().contains("https://");
      if (!containsHttp) updatedVault.setUrl("https://" + updatedVault.getUrl());
      vaultEntity.setUrl(updatedVault.getUrl().trim());
    }
    if (usernameChanged) vaultEntity.setUsername(updatedVault.getUsername().trim());
    if (passwordChanged) vaultEntity.setPassword(updatedVault.getPassword().trim());
    if (categoryChanged) {
      CategoryEntity newCategoryEntity =
          categoryService.findById(updatedVault.getCategory().getId());
      vaultEntity.setCategory(newCategoryEntity);
    }

    vaultRepository.save(vaultEntity);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void insert(VaultEntity newVault) {
    log.info("Inserting a new vault");
    UserEntity authenticatedUser =
        authenticatedUserService
            .findCurrentUser()
            .orElseThrow(
                () ->
                    new LockBoxException(
                        "An error occurred while retrieving the logged-in user",
                        HttpStatus.INTERNAL_SERVER_ERROR));
    CategoryEntity categoryEntity = categoryService.findById(newVault.getCategory().getId());
    newVault.setCategory(categoryEntity);
    boolean containsHttp =
        newVault.getUrl().contains("http://") || newVault.getUrl().contains("https://");
    if (!containsHttp) newVault.setUrl("https://" + newVault.getUrl());
    newVault.setUrl(newVault.getUrl().trim());
    newVault.setUsername(newVault.getUsername().trim());
    newVault.setPassword(newVault.getPassword().trim());
    newVault.setDeleted(false);
    newVault.setCreatedAt(LocalDateTime.now());
    newVault.setUser(authenticatedUser);
    vaultRepository.save(newVault);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void deleteById(Long id) {
    log.info("Deleting a vault by id: {}", id);
    VaultEntity vaultEntity = findById(id);
    vaultRepository.delete(vaultEntity);
  }
}
