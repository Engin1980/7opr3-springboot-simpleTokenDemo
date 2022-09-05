package cz.osu.kip.simpletokendemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping("/user")
public class UserController {

  @Autowired private AppUserRepository appUserRepository;

  @GetMapping("/list")
  public ResponseEntity<List<String>> listNames() {
    List<String> ret = appUserRepository.getAll()
            .stream()
            .map(q -> q.getUsername())
            .toList();
    return ResponseEntity.ok(ret);
  }

  @Autowired
  PasswordEncoder passwordEncoder;
  @Autowired
  TokenManager tokenManager;

  @PostMapping("/login")
  public ResponseEntity<String> login(String username, String password) throws Exception {
    // try to find user
    Optional<AppUser> userOpt = appUserRepository.get(username);
    if (userOpt.isEmpty()) return new ResponseEntity<>("Invalid credentials", HttpStatus.BAD_REQUEST);

    // check user password match
    AppUser user = userOpt.get();
    if (passwordEncoder.matches(password, user.getPassword()) == false)
      return new ResponseEntity<>("Invalid credentials", HttpStatus.BAD_REQUEST);

    // generate token
    final String token = tokenManager.generateTokenFor(user);
    return ResponseEntity.ok(token);
  }

  @PostMapping("/logout")
  public ResponseEntity logout(@Autowired HttpServletRequest request) throws Exception {
    ResponseEntity ret;
    Enumeration<String> tmp = request.getHeaders("Authorization");
    String token;
    try {
      token = tmp.nextElement();
    } catch (NoSuchElementException ex) {
      token = null;
    }
    if (token != null) {
      tokenManager.forgotToken(token);
      ret = ResponseEntity.ok().body("Logged out");
    } else
      ret = ResponseEntity.badRequest().body("Expected token");
    return ret;
  }
}
