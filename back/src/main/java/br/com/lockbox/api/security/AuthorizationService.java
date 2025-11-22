package br.com.lockbox.api.security;

import br.com.lockbox.api.exceptions.LockBoxException;
import br.com.lockbox.api.models.UserEntity;
import br.com.lockbox.api.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorizationService implements UserDetailsService {
  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UserEntity user =
        userRepository
            .findByEmailIgnoreCase(username)
            .orElseThrow(
                () ->
                    new LockBoxException(
                        "no user found for the given email", HttpStatus.NOT_FOUND));
    return new CustomUserDetails(user);
  }
}
