package epam.com.esm.persistence.repository.impl.action;

import epam.com.esm.persistence.entity.impl.action.Order;
import epam.com.esm.persistence.repository.BaseRepository;
import org.springframework.stereotype.Repository;

/**
 * OrderRepository is the repository, provides jpa operations contracts for order
 */
@Repository
public interface OrderRepository extends BaseRepository<Order> {}