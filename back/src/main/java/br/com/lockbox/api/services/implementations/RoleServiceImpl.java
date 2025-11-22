package br.com.lockbox.api.services.implementations;

import br.com.lockbox.api.models.RoleEntity;
import br.com.lockbox.api.repositories.RoleRepository;
import br.com.lockbox.api.services.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
  private final RoleRepository roleRepository;

  @Override
  public List<RoleEntity> findByName(List<String> names) {
    log.info("Finding role by names: {}", names);
    return roleRepository.findByNameInIgnoreCase(names);
  }
}
