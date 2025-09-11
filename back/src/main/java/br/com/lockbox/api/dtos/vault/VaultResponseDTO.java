package br.com.lockbox.api.dtos.vault;

import br.com.lockbox.api.dtos.category.CategoryBasicResponseDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VaultResponseDTO {
  private Long id;
  private String url;
  private String username;
  private String password;
  private CategoryBasicResponseDTO category;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
