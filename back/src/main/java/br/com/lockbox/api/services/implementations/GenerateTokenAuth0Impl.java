package br.com.lockbox.api.services.implementations;

import br.com.lockbox.api.config.provider.EnvProvider;
import br.com.lockbox.api.services.GenerateToken;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Slf4j
@Service
@RequiredArgsConstructor
public class GenerateTokenAuth0Impl implements GenerateToken {

  private final EnvProvider envProvider;

  @Override
  public String generate(Long userId) {
    log.info("Generating token jwt for user: {}", userId);
    Algorithm algorithm = Algorithm.HMAC256(envProvider.getTokenSecret());
    Instant issuedAt = Instant.now();
    Instant expiresAt = issuedAt.plus(envProvider.getTokenDuration());
    return JWT.create()
        .withIssuer(envProvider.getTokenIssuer())
        .withSubject(userId.toString())
        .withIssuedAt(issuedAt)
        .withExpiresAt(expiresAt)
        .sign(algorithm);
  }
}
