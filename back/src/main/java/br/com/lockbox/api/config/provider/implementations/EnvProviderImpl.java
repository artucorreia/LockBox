package br.com.lockbox.api.config.provider.implementations;

import br.com.lockbox.api.config.provider.EnvProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class EnvProviderImpl implements EnvProvider {
  @Value("${security.token.issuer}")
  private String tokenIssuer;

  @Value("${security.token.secret}")
  private String tokenSecret;

  @Value("${security.token.duration}")
  private Duration tokenDuration;

  @Override
  public String getTokenIssuer() {
    return tokenIssuer;
  }

  @Override
  public String getTokenSecret() {
    return tokenSecret;
  }

  @Override
  public Duration getTokenDuration() {
    return tokenDuration;
  }
}
