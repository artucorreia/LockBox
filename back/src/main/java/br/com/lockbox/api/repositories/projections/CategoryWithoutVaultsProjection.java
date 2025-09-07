package br.com.lockbox.api.repositories.projections;

import java.time.LocalDateTime;

public interface CategoryWithoutVaultsProjection {
  Long getId();

  String getName();

  LocalDateTime getCreatedAt();

  LocalDateTime getUpdatedAt();
}
