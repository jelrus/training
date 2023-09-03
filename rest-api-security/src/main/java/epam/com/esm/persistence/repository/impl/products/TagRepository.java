package epam.com.esm.persistence.repository.impl.products;

import epam.com.esm.persistence.entity.impl.products.Tag;
import epam.com.esm.persistence.repository.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * TagRepository is the repository, provides jpa operations contracts for tag
 */
@Repository
public interface TagRepository extends BaseRepository<Tag> {

    /**
     * Contact for checking existence by name
     *
     * @param name provided name
     * @return {@code true} if exists by name
     */
    Boolean existsByName(String name);

    /**
     * Contract for finding by name
     *
     * @param name provided name
     * @return {@code Optional<Tag>} result of finding
     */
    Optional<Tag> findByName(String name);
}