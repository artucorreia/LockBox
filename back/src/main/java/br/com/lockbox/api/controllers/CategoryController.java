package br.com.lockbox.api.controllers;

import br.com.lockbox.api.dtos.category.CategoryWithVaultsResponseDTO;
import br.com.lockbox.api.mappers.CategoryMapper;
import br.com.lockbox.api.dtos.ResponseDTO;
import br.com.lockbox.api.dtos.category.CategoryResponseDTO;
import br.com.lockbox.api.dtos.category.CreateCategoryDTO;
import br.com.lockbox.api.models.CategoryEntity;
import br.com.lockbox.api.services.CategoryService;
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
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {
  private final CategoryService categoryService;
  private final CategoryMapper categoryMapper;

  @GetMapping(value = "/{id}/vaults", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ResponseDTO<CategoryWithVaultsResponseDTO>> findById(@PathVariable Long id) {
    CategoryEntity categoryEntity = categoryService.findById(id);
    CategoryWithVaultsResponseDTO categoryResponseDTO = categoryMapper.entityToWithVaultsResponseDTO(categoryEntity);
    ResponseDTO<CategoryWithVaultsResponseDTO> response =
        new ResponseDTO<>(true, null, HttpStatus.OK.value(), categoryResponseDTO);
    return ResponseEntity.ok(response);
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ResponseDTO<List<CategoryResponseDTO>>> findAll(
      @RequestParam(name = "pagination", required = false, defaultValue = "false")
          Boolean withPagination,
      @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
      @RequestParam(name = "size", required = false, defaultValue = "6") Integer size,
      @RequestParam(name = "direction", required = false, defaultValue = "asc") String direction) {

    if (!withPagination) {
      List<CategoryEntity> categoryEntities = categoryService.findAll();
      List<CategoryResponseDTO> categoryResponseDTOS =
          categoryMapper.entityToResponseDTO(categoryEntities);

      ResponseDTO<List<CategoryResponseDTO>> response =
          new ResponseDTO<>(true, null, HttpStatus.OK.value(), categoryResponseDTOS);
      return ResponseEntity.ok(response);
    }

    Sort.Direction sortDirection =
        "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;

    Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "createdAt"));

    Page<CategoryEntity> categoryEntities = categoryService.findAll(pageable);
    List<CategoryResponseDTO> categoryResponseDTOS =
        categoryEntities.map(categoryMapper::entityToResponseDTO).getContent();

    ResponseDTO<List<CategoryResponseDTO>> response =
        new ResponseDTO<>(true, null, HttpStatus.OK.value(), categoryResponseDTOS);
    return ResponseEntity.ok(response);
  }

  @PostMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ResponseDTO<Object>> insert(
      @Valid @RequestBody CreateCategoryDTO createCategoryDTO) {
    CategoryEntity categoryEntity = categoryMapper.createDTOToEntity(createCategoryDTO);
    categoryService.insert(categoryEntity);
    ResponseDTO<Object> response =
        new ResponseDTO<>(true, "category created successfully", HttpStatus.CREATED.value(), null);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @DeleteMapping(value = "/{id}")
  public ResponseEntity<ResponseDTO<CategoryResponseDTO>> deleteById(@PathVariable Long id) {
    categoryService.deleteById(id);
    ResponseDTO<CategoryResponseDTO> response =
        new ResponseDTO<>(true, "category deleted successfully", HttpStatus.OK.value(), null);
    return ResponseEntity.ok(response);
  }
}
