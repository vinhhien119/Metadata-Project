package application.model;

/**
 * Represents an authentication response.
 */
public class AuthenticationResponse {
  private String token;
  private String message;
  private Role role;
  private String username;

  /**
   * Creates a new AuthenticationResponse with the provided token, message, role, and username.
   * 
   * @param token for authentication
   * @param message the authentication message
   * @param role of the user
   * @param username of the user
   */
  public AuthenticationResponse(String token, String message, Role role, String username) {
    this.token = token;
    this.message = message;
    this.role = role;
    this.username = username;
  }

  public String getToken() {
    return token;
  }

  public String getMessage() {
    return message;
  }

  public Role getRole() {
    return role;
  }

  public String getUsername() {
    return username;
  }
}
