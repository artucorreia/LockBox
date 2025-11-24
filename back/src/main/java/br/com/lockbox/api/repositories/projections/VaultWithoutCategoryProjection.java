package br.com.lockbox.api.repositories.projections;

public interface VaultWithoutCategoryProjection {
  Long getId();

  String getUrl();

  String getUsername();

  String getPassword();
}
