package br.com.lockbox.api.seeders;

import br.com.lockbox.api.models.RoleEntity;
import br.com.lockbox.api.models.UserEntity;
import br.com.lockbox.api.repositories.RoleRepository;
import br.com.lockbox.api.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserSeeder implements Seeder {
  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

  @Override
  public void run() {
    if (userRepository.count() != 0) {
      log.info("There are already users. Skipping seeder.");
      return;
    }
    log.info("Inserting users...");
    Collection<UserEntity> usersToInsert = getEntities();
    userRepository.saveAll(usersToInsert);
    log.info("Users inserted successfully.");
  }

  private Collection<UserEntity> getEntities() {
    List<RoleEntity> roles = roleRepository.findByNameInIgnoreCase(List.of("COMMON"));

    UserEntity firstUser = new UserEntity();
    firstUser.setName("Arthur Correia");
    firstUser.setEmail("arthurcorreia.dev@gmail.com");
    firstUser.setRoles(roles);
    firstUser.setPassword(encoder.encode("12345678"));
    firstUser.setPasswordKey("123");
    firstUser.setCreatedAt(LocalDateTime.now());
    firstUser.setDeleted(false);
    return new ArrayList<>(List.of(firstUser));
  }
}
