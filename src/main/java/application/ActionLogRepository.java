package application;

import org.springframework.data.repository.CrudRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called actionLogRepository
// CRUD refers Create, Read, Update, Delete

/**
 * This interface holds the log of actions and is automatically implemented.
 *
 * @author Sarah Haines
 */

public interface ActionLogRepository extends CrudRepository<ActionLog, Integer> {

}