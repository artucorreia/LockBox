package br.com.lockbox.api.services.implementations;

import br.com.lockbox.api.exceptions.LockBoxException;
import br.com.lockbox.api.models.RoleEntity;
import br.com.lockbox.api.models.UserEntity;
import br.com.lockbox.api.repositories.UserRepository;
import br.com.lockbox.api.services.RoleService;
import br.com.lockbox.api.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private final RoleService roleService;
  private final PasswordEncoder encoder = new BCryptPasswordEncoder();

  @Override
  public UserEntity findById(Long id) {
    log.info("Finding user by id: {}", id);
    return userRepository
        .findById(id)
        .orElseThrow(
            () -> new LockBoxException("no user found for the given id", HttpStatus.NOT_FOUND));
  }

  @Override
  public UserEntity findByEmail(String email) {
    log.info("Finding user by email: {}", email);
    return userRepository
        .findByEmailIgnoreCase(email.trim())
        .orElseThrow(
            () -> new LockBoxException("no user found for the given email", HttpStatus.NOT_FOUND));
  }

  @Override
  public void insert(UserEntity user) {
    log.info("Inserting a new user");
    Optional<UserEntity> optionalUserEntity = userRepository.findByEmailIgnoreCase(user.getEmail().trim());
    if (optionalUserEntity.isPresent())
      throw new LockBoxException("This email address is already in use", HttpStatus.BAD_REQUEST);

    user.setName(user.getName().trim());
    user.setEmail(user.getEmail().trim());
    user.setCreatedAt(LocalDateTime.now());
    user.setDeleted(false);
    String password = encoder.encode(user.getPassword());
    String passwordKey = encoder.encode(user.getPasswordKey());
    List<RoleEntity> roles = roleService.findByName(List.of("COMMON"));
    user.setPassword(password);
    user.setPasswordKey(passwordKey);
    user.setRoles(roles);
    userRepository.save(user);
  }
}
