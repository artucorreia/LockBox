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
public class CreateVaultDTO {
  @NotBlank(message = "the vault url is mandatory")
  @Size(min = 5, max = 255, message = "the vault url length must be between 3 and 50 characters")
  private String url;

  @NotBlank(message = "the vault username is mandatory")
  @Size(min = 3, max = 100, message = "the vault username length must be between 3 and 50 characters")
  private String username;

  @NotBlank(message = "the vault password is mandatory")
  @Size(min = 8, max = 255, message = "the vault password length must be between 3 and 50 characters")
  private String password;

  @NotNull(message = "the vault category id is mandatory")
  @Min(value = 1, message = "the vault category id must be greater than 1")
  private Long categoryId;
}
