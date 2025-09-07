package br.com.lockbox.api.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@Setter
@ResponseStatus
public class LockBoxException extends RuntimeException {
  private HttpStatus status;

  public LockBoxException(String message, HttpStatus status) {
    super(message);
    this.status = status;
  }
}
