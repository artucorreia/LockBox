package br.com.lockbox.api.mappers;

import br.com.lockbox.api.dtos.auth.RegisterDTO;
import br.com.lockbox.api.models.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface UserMapper {
  @Mappings(
      value = {
        @Mapping(target = "id", ignore = true),
        @Mapping(target = "roles", ignore = true),
        @Mapping(target = "vaults", ignore = true),
        @Mapping(target = "categories", ignore = true),
      })
  UserEntity registerDTOToEntity(RegisterDTO registerDTO);
}
