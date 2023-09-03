package epam.com.esm.persistence.repository.impl.purchase;

import epam.com.esm.persistence.entity.impl.purchase.PurchaseData;
import epam.com.esm.persistence.repository.BaseRepository;
import org.springframework.stereotype.Repository;

/**
 * PurchaseDataRepository is the repository, provides jpa operations contracts for purchase data
 */
@Repository
public interface PurchaseDataRepository extends BaseRepository<PurchaseData> {}