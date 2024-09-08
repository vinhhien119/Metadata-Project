package application.repository;

import application.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * This interface holds the user records and is automatically implemented.
 */
public interface UserRepository extends JpaRepository<User, Integer> {

  Optional<User> findByUsername(String username);

  List<User> findAll();

}
