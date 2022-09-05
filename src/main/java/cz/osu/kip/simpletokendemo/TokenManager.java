package cz.osu.kip.simpletokendemo;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

@Service
public class TokenManager {
  private final static int TOKEN_LENGTH = 32;
  private final Random random = new Random();
  private final Map<String, AppUser> inner = new HashMap<>();

  public void forgotToken(String token) {
    inner.remove(token);
  }

  public String generateTokenFor(AppUser user) {
    String token = generateToken();
    inner.put(token, user);
    return token;
  }

  public Optional<AppUser> validateAndGetUser(String token) {
    Optional<AppUser> ret;
    if (inner.containsKey(token))
      ret = Optional.of(inner.get(token));
    else
      ret = Optional.empty();
    return ret;
  }

  private String generateToken() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < TOKEN_LENGTH; i++) {
      char c = (char) random.nextInt('!', 'z' + 1);
      sb.append(c);
    }
    return sb.toString();
  }
}
