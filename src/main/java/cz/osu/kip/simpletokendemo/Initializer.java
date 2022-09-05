package cz.osu.kip.simpletokendemo;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class Initializer implements CommandLineRunner {
  @Autowired private PasswordEncoder passwordEncoder;
  @Autowired private AppUserRepository appUserRepository;
  @Autowired private Logger logger;

  @Override
  public void run(String... args) throws Exception {
    logger.info("Creating new users in AppUserRepository");
    AppUser user;
    user = new AppUser("Terez",
            passwordEncoder.encode("heslo"));
    appUserRepository.add(user);
    user = new AppUser("Orech",
            passwordEncoder.encode("strom"));
    appUserRepository.add(user);
    logger.info("Users inserted.");
  }
}
