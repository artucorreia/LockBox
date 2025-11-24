package br.com.lockbox.api.seeders;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DataLoaderRunner implements CommandLineRunner {
  private final Seeder roleSeeder, userSeeder;

  public DataLoaderRunner(RoleSeeder roleSeeder, UserSeeder userSeeder) {
    this.roleSeeder = roleSeeder;
    this.userSeeder = userSeeder;
  }

  @Override
  public void run(String... args) throws Exception {
    if (!Arrays.stream(args).toList().contains("--seed")) return;
    roleSeeder.run();
    userSeeder.run();
  }
}
