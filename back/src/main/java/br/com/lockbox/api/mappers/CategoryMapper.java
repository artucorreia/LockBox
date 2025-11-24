package br.com.lockbox.api.mappers;

import br.com.lockbox.api.dtos.category.CategoryResponseDTO;
import br.com.lockbox.api.dtos.category.CategoryWithVaultsResponseDTO;
import br.com.lockbox.api.dtos.category.CreateCategoryDTO;
import br.com.lockbox.api.models.CategoryEntity;
import br.com.lockbox.api.repositories.projections.CategoryWithoutVaultsProjection;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(
    componentModel = "spring",
    uses = {VaultMapper.class})
public interface CategoryMapper {

  CategoryResponseDTO entityToResponseDTO(CategoryEntity categoryEntity);

  List<CategoryResponseDTO> entityToResponseDTO(List<CategoryEntity> categoryEntity);

  @Mappings(
      value = {
        @Mapping(target = "id", ignore = true),
        @Mapping(target = "vaults", ignore = true),
        @Mapping(target = "createdAt", ignore = true),
        @Mapping(target = "updatedAt", ignore = true)
      })
  CategoryEntity createDTOToEntity(CreateCategoryDTO createCategoryDTO);

  @Mappings(value = {@Mapping(target = "vaults", ignore = true)})
  CategoryEntity projectionWithoutVaultsToEntity(
      CategoryWithoutVaultsProjection categoryWithoutVaultsProjection);

  CategoryWithVaultsResponseDTO entityToWithVaultsResponseDTO(CategoryEntity categoryEntity);

  List<CategoryWithVaultsResponseDTO> entityToWithVaultsResponseDTO(
      List<CategoryEntity> categoryEntities);
}
