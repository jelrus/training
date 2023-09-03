package epam.com.esm.persistence.repository.impl.user;

import epam.com.esm.persistence.entity.impl.user.component.Role;
import epam.com.esm.persistence.repository.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * RoleRepository is the repository, provides jpa operations contracts for role
 */
@Repository
public interface RoleRepository extends BaseRepository<Role> {

    /**
     * Contract for finding by name
     *
     * @param name provided name
     * @return {@code Optional<Role>} result of finding
     */
    Optional<Role> findByName(String name);
}