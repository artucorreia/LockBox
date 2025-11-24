package br.com.lockbox.api.dtos.category;

import br.com.lockbox.api.dtos.vault.VaultBasicResponseDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryWithVaultsResponseDTO {
  private Long id;
  private String name;
  private List<VaultBasicResponseDTO> vaults;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
