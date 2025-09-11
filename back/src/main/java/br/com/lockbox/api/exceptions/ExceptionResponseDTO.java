package br.com.lockbox.api.exceptions;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@SuperBuilder
public class ExceptionResponseDTO {
  private Boolean success;
  private String message;
  private Integer code;
  private String uri;
  private LocalDateTime timestamp;
}
