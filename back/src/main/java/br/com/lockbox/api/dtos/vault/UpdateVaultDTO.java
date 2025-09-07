package br.com.lockbox.api.dtos.vault;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateVaultDTO {
  @Size(min = 5, max = 255, message = "the vault url length must be between 3 and 50 characters")
  private String url;

  @Size(min = 3, max = 100, message = "the vault username length must be between 3 and 50 characters")
  private String username;

  @Size(min = 8, max = 255, message = "the vault password length must be between 3 and 50 characters")
  private String password;

  @Min(value = 1, message = "the vault category id must be greater than 1")
  private Long categoryId;
}
