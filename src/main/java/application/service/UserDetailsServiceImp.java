package application.service;

import application.model.Role;
import application.model.User;
import application.repository.UserRepository;
import java.util.Optional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;



/**
 * Service class for loading user details and updating user roles.
 */
@Service
public class UserDetailsServiceImp implements UserDetailsService {

  private final UserRepository repository;

  public UserDetailsServiceImp(UserRepository repository) {
    this.repository = repository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return repository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
  }
  
  /**
   * Updates the role of a user.
   *
   * @param username of user to be updated
   * @param newRole to be assigned
   * @throws UsernameNotFoundException if the user with the specified username is not found
   */
  public void updateRole(String username, Role newRole) {
    Optional<User> userOptional = repository.findByUsername(username);
    if (userOptional.isPresent()) {
      User user = userOptional.get();
      user.setRole(newRole);
      repository.save(user);
    } else {
      throw new UsernameNotFoundException("User with username " + username + " not found");
    }
  }
}
