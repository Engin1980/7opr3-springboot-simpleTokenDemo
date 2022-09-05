package cz.osu.kip.simpletokendemo;

import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class AppUserRepository {
  private final List<AppUser> users = new ArrayList<>();

  public void add(AppUser user) {
    this.users.add(user);
  }

  public Optional<AppUser> get(String username) {
    return users.stream()
            .filter(q -> q.getUsername().equalsIgnoreCase(username))
            .findFirst();
  }

  public List<AppUser> getAll() {
    return new ArrayList<>(this.users);
  }
}
