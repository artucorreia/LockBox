package br.com.lockbox.api.services.implementations;

import br.com.lockbox.api.exceptions.LockBoxException;
import br.com.lockbox.api.mappers.VaultMapper;
import br.com.lockbox.api.models.CategoryEntity;
import br.com.lockbox.api.models.VaultEntity;
import br.com.lockbox.api.repositories.VaultRepository;
import br.com.lockbox.api.services.CategoryService;
import br.com.lockbox.api.services.VaultService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class VaultServiceImpl implements VaultService {
  private final Logger LOGGER = Logger.getLogger(VaultServiceImpl.class.getName());
  private final VaultRepository vaultRepository;
  private final CategoryService categoryService;
  private final VaultMapper vaultMapper;

  @Override
  public VaultEntity findById(Long id) {
    LOGGER.info("Finding a vault by id");
    return vaultRepository
        .findById(id)
        .orElseThrow(() -> new LockBoxException("no vault found for the given id", HttpStatus.NOT_FOUND));
  }

  @Override
  public List<VaultEntity> findAll() {
    LOGGER.info("Finding all vaults");
    return vaultRepository.findAll();
  }

  @Override
  public Page<VaultEntity> findAll(Pageable pageable) {
    LOGGER.info("Finding all vaults with pagination");
    return vaultRepository.findAllBy(pageable).map(vaultMapper::withoutCategoryProjectionToEntity);
  }

  @Override
  public void update(Long id, VaultEntity updatedVault) {
    VaultEntity vaultEntity = findById(id);

    boolean urlChanged =
        updatedVault.getUrl() != null && !vaultEntity.getUrl().equals(updatedVault.getUrl());
    boolean usernameChanged =
        updatedVault.getUsername() != null
            && !vaultEntity.getUsername().equals(updatedVault.getUsername());
    boolean passwordChanged =
        updatedVault.getPassword() != null
            && !vaultEntity.getPassword().equals(updatedVault.getPassword());

    if (urlChanged) vaultEntity.setUrl(updatedVault.getUrl().trim());
    if (usernameChanged) vaultEntity.setUsername(updatedVault.getUsername().trim());
    if (passwordChanged) vaultEntity.setPassword(updatedVault.getPassword().trim());
    // TODO: update category

    vaultRepository.save(vaultEntity);
  }

  @Override
  public void insert(VaultEntity newVault) {
    LOGGER.info("Inserting a new vault");

    CategoryEntity categoryEntity = categoryService.findById(newVault.getCategory().getId());
    newVault.setCategory(categoryEntity);
    // TODO: add http in url
    newVault.setUrl(newVault.getUrl().trim());
    newVault.setUsername(newVault.getUsername().trim());
    newVault.setPassword(newVault.getPassword().trim());

    vaultRepository.save(newVault);
  }

  @Override
  public void deleteById(Long id) {
    LOGGER.info("Deleting a vault by id");
    VaultEntity vaultEntity = findById(id);
    vaultRepository.delete(vaultEntity);
  }
}
