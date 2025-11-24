package br.com.lockbox.api.mappers;

import br.com.lockbox.api.dtos.vault.CreateVaultDTO;
import br.com.lockbox.api.dtos.vault.UpdateVaultDTO;
import br.com.lockbox.api.dtos.vault.VaultResponseDTO;

import br.com.lockbox.api.models.VaultEntity;
import br.com.lockbox.api.repositories.projections.VaultWithoutCategoryProjection;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(
    componentModel = "spring",
    uses = {CategoryMapper.class})
public interface VaultMapper {
  VaultResponseDTO entityToResponseDTO(VaultEntity vaultEntity);

  List<VaultResponseDTO> entityToResponseDTO(List<VaultEntity> vaultEntities);

  @Mappings(
      value = {
        @Mapping(target = "id", ignore = true),
        @Mapping(target = "category.id", source = "categoryId"),
        @Mapping(target = "createdAt", ignore = true),
        @Mapping(target = "updatedAt", ignore = true)
      })
  VaultEntity createDTOToEntity(CreateVaultDTO createVaultDTO);

  @Mappings(
      value = {
        @Mapping(target = "id", ignore = true),
        @Mapping(target = "category.id", source = "categoryId"),
        @Mapping(target = "createdAt", ignore = true),
        @Mapping(target = "updatedAt", ignore = true)
      })
  VaultEntity updateDTOToEntity(UpdateVaultDTO updateVaultDTO);

  @Mappings(
      value = {
        @Mapping(target = "category", ignore = true),
      })
  VaultEntity withoutCategoryProjectionToEntity(
      VaultWithoutCategoryProjection vaultWithoutCategoryProjection);
}
