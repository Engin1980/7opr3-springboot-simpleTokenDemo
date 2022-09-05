package cz.osu.kip.simpletokendemo;

public class AppUser {
  private final String username;
  private final String password;

  public AppUser(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }
}
