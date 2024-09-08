package application.controller;

import application.DTO.UserDTO;
import application.model.Role;
import application.model.User;
import application.repository.UserRepository;
import application.service.UserDetailsServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Controller for user related methods.
 */
@RestController
public class UserController {

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private UserDetailsServiceImp userService;

  /**
   * Finds all users from the database.
   * @return users
   */
  @GetMapping("/user/find/all")
  public ResponseEntity<List<UserDTO>> getAllUsers() {
    List<User> users = userRepository.findAll();
    List<UserDTO> usersDTOs = new ArrayList<>();

    for (User user : users) {
      UserDTO userDTO = new UserDTO();
      userDTO.setID(user.getId());
      userDTO.setFirstname(user.getFirstName());
      userDTO.setLastname(user.getLastName());
      userDTO.setUsername(user.getUsername());
      userDTO.setRole(user.getRole());
      usersDTOs.add(userDTO);
    }

    return ResponseEntity.ok(usersDTOs);
  }


  /**
   * Find role of specified user from the database.
   * 
   * @param username of the user 
   * @param requestBody containing role information
   * @return role of user
   */
  @PutMapping("user/{username}/role")
  public ResponseEntity<String> updateRole(@PathVariable String username,
      @RequestBody Map<String, String> requestBody) {
    String roleValue = requestBody.get("role");
    Role role = Role.valueOf(roleValue.toUpperCase());
    userService.updateRole(username, role);
    UserDetails updatedUser = userService.loadUserByUsername(username);
    return ResponseEntity.ok(updatedUser.getAuthorities().toString());
  }

  /**
   * Find user by username. 
   * 
   * @param username of user
   * @return user
   */
  @GetMapping("user/{username}")
  public ResponseEntity<UserDTO> getUser(@PathVariable String username) {
    Optional<User> optionalUser = userRepository.findByUsername(username);
    if (optionalUser.isPresent()) {
      User user = optionalUser.get();
      UserDTO userDTO = new UserDTO();
      userDTO.setID(user.getId());
      userDTO.setFirstname(user.getFirstName());
      userDTO.setLastname(user.getLastName());
      userDTO.setUsername(user.getUsername());
      userDTO.setRole(user.getRole());
      return ResponseEntity.ok(userDTO);
    } else {
      return ResponseEntity.notFound().build(); // User not found
    }
  }
}
