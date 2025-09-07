package br.com.lockbox.api.dtos.category;

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
public class CreateCategoryDTO {
  @NotBlank(message = "the category name is mandatory")
  @Size(min = 3, max = 12, message = "the category name length must be between 3 and 12 characters")
  private String name;
}
