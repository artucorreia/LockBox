package br.com.lockbox.api.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserEntity extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 50)
  private String name;

  @Column(nullable = false, length = 60)
  private String email;

  @Column(nullable = false, length = 100)
  private String password;

  @Column(name = "password_key", nullable = false, length = 50)
  private String passwordKey;

  @ManyToMany(mappedBy = "users")
  private List<RoleEntity> roles;

  @OneToMany(mappedBy = "user")
  private List<VaultEntity> vaults;

  @OneToMany(mappedBy = "user")
  private List<CategoryEntity> categories;
}
