package application;

import org.springframework.data.repository.CrudRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called assetCommentRepository
// CRUD refers Create, Read, Update, Delete

/**
 * This interface holds the asset comments and is automatically implemented.
 *
 * @author Sarah Haines
 */

public interface AssetCommentRepository extends CrudRepository<AssetComment, Integer> {

}