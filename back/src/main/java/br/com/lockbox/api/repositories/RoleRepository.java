package br.com.lockbox.api.repositories;

import br.com.lockbox.api.models.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
  List<RoleEntity> findByNameInIgnoreCase(List<String> names);
}
