package br.com.lockbox.api.dtos.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class TokenResponseDTO {
  private Long userId;
  private String userName;
  private String token;
}
