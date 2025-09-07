package br.com.lockbox.api.controllers;

import br.com.lockbox.api.dtos.category.CategoryWithVaultsResponseDTO;
import br.com.lockbox.api.mappers.CategoryMapper;
import br.com.lockbox.api.mappers.VaultMapper;
import br.com.lockbox.api.dtos.ResponseDTO;
import br.com.lockbox.api.dtos.vault.CreateVaultDTO;
import br.com.lockbox.api.dtos.vault.UpdateVaultDTO;
import br.com.lockbox.api.dtos.vault.VaultResponseDTO;
import br.com.lockbox.api.models.CategoryEntity;
import br.com.lockbox.api.models.VaultEntity;
import br.com.lockbox.api.services.CategoryService;
import br.com.lockbox.api.services.VaultService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/vaults")
@RequiredArgsConstructor
public class VaultController {
  private final VaultService vaultService;
  private final CategoryService categoryService;
  private final CategoryMapper categoryMapper;
  private final VaultMapper vaultMapper;

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ResponseDTO<VaultResponseDTO>> findById(@PathVariable Long id) {
    VaultEntity vaultEntity = vaultService.findById(id);
    VaultResponseDTO vaultResponseDTO = vaultMapper.entityToResponseDTO(vaultEntity);
    ResponseDTO<VaultResponseDTO> response =
        new ResponseDTO<>(true, null, HttpStatus.OK.value(), vaultResponseDTO);
    return ResponseEntity.ok(response);
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ResponseDTO<List<VaultResponseDTO>>> findAll(
      @RequestParam(name = "pagination", required = false, defaultValue = "false")
          Boolean withPagination,
      @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
      @RequestParam(name = "size", required = false, defaultValue = "6") Integer size,
      @RequestParam(name = "direction", required = false, defaultValue = "asc") String direction) {
    if (!withPagination) {
      List<VaultEntity> vaultEntities = vaultService.findAll();
      List<VaultResponseDTO> vaultResponseDTOS = vaultMapper.entityToResponseDTO(vaultEntities);
      ResponseDTO<List<VaultResponseDTO>> response =
          new ResponseDTO<>(true, null, HttpStatus.OK.value(), vaultResponseDTOS);
      return ResponseEntity.ok(response);
    }

    Sort.Direction sortDirection =
        "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
    Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "createdAt"));
    Page<VaultEntity> vaultEntityPage = vaultService.findAll(pageable);
    List<VaultResponseDTO> vaultResponseDTOS =
        vaultEntityPage.map(vaultMapper::entityToResponseDTO).getContent();
    ResponseDTO<List<VaultResponseDTO>> response =
        new ResponseDTO<>(true, null, HttpStatus.OK.value(), vaultResponseDTOS);
    return ResponseEntity.ok(response);
  }

  @GetMapping(value = "/grouped", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ResponseDTO<List<CategoryWithVaultsResponseDTO>>> findAllGrouped() {
    List<CategoryEntity> categoryEntities = categoryService.findAllWithVaults();
    List<CategoryWithVaultsResponseDTO> categoryWithVaultsResponseDTOS =
        categoryMapper.entityToWithVaultsResponseDTO(categoryEntities);
    ResponseDTO<List<CategoryWithVaultsResponseDTO>> response =
        new ResponseDTO<>(true, null, HttpStatus.OK.value(), categoryWithVaultsResponseDTOS);
    return ResponseEntity.ok(response);
  }


  @PostMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ResponseDTO<Object>> insert(
      @Valid @RequestBody CreateVaultDTO createVaultDTO) {
    VaultEntity vaultEntity = vaultMapper.createDTOToEntity(createVaultDTO);
    vaultService.insert(vaultEntity);
    ResponseDTO<Object> response =
        new ResponseDTO<>(true, "vault created successfully", HttpStatus.CREATED.value(), null);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }


  @PutMapping(
      value = "/{id}",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ResponseDTO<List<VaultResponseDTO>>> update(
      @PathVariable Long id, @Valid @RequestBody UpdateVaultDTO updateVaultDTO) {
    VaultEntity vaultEntity = vaultMapper.updateDTOToEntity(updateVaultDTO);
    vaultService.update(id, vaultEntity);
    ResponseDTO<List<VaultResponseDTO>> response =
        new ResponseDTO<>(true, "vault updated successfully", HttpStatus.OK.value(), null);
    return ResponseEntity.ok(response);
  }


  @DeleteMapping(value = "/{id}")
  public ResponseEntity<ResponseDTO<VaultResponseDTO>> deleteById(@PathVariable Long id) {
    vaultService.deleteById(id);
    ResponseDTO<VaultResponseDTO> response =
        new ResponseDTO<>(true, "vault deleted successfully", HttpStatus.OK.value(), null);
    return ResponseEntity.ok(response);
  }
}
