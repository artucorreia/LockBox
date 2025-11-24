package br.com.lockbox.api.seeders;

import br.com.lockbox.api.models.RoleEntity;
import br.com.lockbox.api.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleSeeder implements Seeder {

  private final RoleRepository roleRepository;

  @Override
  public void run() {
    if (roleRepository.count() != 0) {
      log.info("There are already roles. Skipping seeder.");
      return;
    }
    log.info("Inserting roles...");
    Collection<RoleEntity> rolesToInsert = getEntities();
    roleRepository.saveAll(rolesToInsert);
    log.info("Roles inserted successfully.");
  }

  private Collection<RoleEntity> getEntities() {
    RoleEntity commonRole = new RoleEntity();
    commonRole.setName("COMMON");
    commonRole.setCreatedAt(LocalDateTime.now());
    commonRole.setDeleted(false);
    return new ArrayList<>(List.of(commonRole));
  }
}
