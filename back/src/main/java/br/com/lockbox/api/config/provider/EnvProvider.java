package br.com.lockbox.api.config.provider;

import java.time.Duration;

public interface EnvProvider {
  //  Duration getRefreshTokenDuration();

  String getTokenIssuer();

  String getTokenSecret();

  Duration getTokenDuration();
}
