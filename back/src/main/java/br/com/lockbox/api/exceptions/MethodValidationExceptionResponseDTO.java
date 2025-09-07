package br.com.lockbox.api.exceptions;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@SuperBuilder
public class MethodValidationExceptionResponseDTO extends ExceptionResponseDTO {
  private Map<String, String> fields;
}
