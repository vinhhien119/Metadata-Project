package application;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


/**
 * This interface holds the asset records and is automatically implemented.
 */
public interface AssetRepository
    extends JpaRepository<Asset, Integer>, JpaSpecificationExecutor<Asset> {
  List<Asset> findByType(String type);
  boolean existsByTitle(String title);
}
