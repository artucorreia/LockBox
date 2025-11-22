package br.com.lockbox.api.dtos.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegisterDTO {
  @NotBlank(message = "the name is mandatory")
  @Size(min = 2, max = 50, message = "the name length must be between 2 and 50 characters")
  private String name;

  @Email(message = "the email must be in a valid format")
  @NotBlank(message = "the email is mandatory")
  @Size(min = 12, max = 60, message = "the email length must be between 12 and 60 characters")
  private String email;

  @NotBlank(message = "the password is mandatory")
  @Size(min = 8, max = 50, message = "the password length must be between 8 and 50 characters")
  private String password;

  @NotBlank(message = "the password key is mandatory")
  @Size(min = 4, max = 20, message = "the password key length must be between 4 and 20 characters")
  private String passwordKey;
}
