
package br.com.murilocb123.portflow.repositories;

import br.com.murilocb123.portflow.domain.entities.AssetHistoryEntity;
import br.com.murilocb123.portflow.domain.identifier.AssetPriceId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssetHistoryRepository extends JpaRepository<AssetHistoryEntity, AssetPriceId> {
}