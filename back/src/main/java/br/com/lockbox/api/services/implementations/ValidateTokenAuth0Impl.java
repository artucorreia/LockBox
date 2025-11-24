package br.com.lockbox.api.services.implementations;

import br.com.lockbox.api.config.provider.EnvProvider;
import br.com.lockbox.api.services.ValidateToken;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ValidateTokenAuth0Impl implements ValidateToken {

  private final EnvProvider envProvider;

  @Override
  public Long validate(String token) {
    log.info("Validating token: {}", token);
    Algorithm algorithm = Algorithm.HMAC256(envProvider.getTokenSecret());
    String subject =
        JWT.require(algorithm)
            .withIssuer(envProvider.getTokenIssuer())
            .build()
            .verify(token)
            .getSubject();
    return Long.parseLong(subject);
  }
}
