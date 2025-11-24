package br.com.lockbox.api.security;

import br.com.lockbox.api.exceptions.LockBoxException;
import br.com.lockbox.api.models.UserEntity;
import br.com.lockbox.api.services.UserService;
import br.com.lockbox.api.services.ValidateToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

  private final UserService userService;
  private final ValidateToken validateToken;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    try {
      String token = recoverToken(request);
      if (token != null) {
        Long userId = validateToken.validate(token);
        if (userId == null)
          throw new LockBoxException("Invalid or expired token", HttpStatus.FORBIDDEN);

        UserEntity user = userService.findById(userId);
        if (user.getDeleted()) throw new LockBoxException("Inactive user", HttpStatus.FORBIDDEN);

        CustomUserDetails customUserDetails = new CustomUserDetails(user);

        UsernamePasswordAuthenticationToken authentication =
            new UsernamePasswordAuthenticationToken(
                customUserDetails.getUser(), null, customUserDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
      }

      filterChain.doFilter(request, response);
    } catch (Exception ex) {
      throw new LockBoxException("token validation failed", HttpStatus.FORBIDDEN);
    }
  }

  private String recoverToken(HttpServletRequest request) {
    String authHeader = request.getHeader("Authorization");
    if (authHeader == null) return null;
    return authHeader.replace("Bearer ", "");
  }
}
