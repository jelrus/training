package epam.com.esm.persistence.repository.impl.user;

import epam.com.esm.persistence.entity.impl.user.User;
import epam.com.esm.persistence.repository.BaseRepository;
import epam.com.esm.utils.statistics.repository.ResultTag;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * UserRepository is the repository, provides jpa operations contracts for user
 */
@Repository
public interface UserRepository extends BaseRepository<User> {

    /**
     * Contact for checking existence by username
     *
     * @param username provided username
     * @return {@code true} if exists by username
     */
    Boolean existsByUsername(String username);

    /**
     * Contract for finding by username
     *
     * @param username provided name
     * @return {@code Optional<User>} result of finding
     */
    Optional<User> findByUsername(String username);

    /**
     * Contract for finding tags by popularity and order max cost
     *
     * @param userId provided user id
     * @return {@code List<ResultTag>} collection of result tags objects
     */
    @Query("SELECT new epam.com.esm.utils.statistics.repository.ResultTag(tag.name, MAX(o.cost), COUNT(tag.name)) " +
            "FROM User u " +
            "JOIN u.orders o " +
            "JOIN o.giftCertificates gc " +
            "JOIN gc.tags tag " +
            "WHERE u.id = :userId " +
            "GROUP BY tag.name " +
            "ORDER BY COUNT(tag.name) DESC")
    List<ResultTag> findTagsByPopularityAndMaxCost(@Param("userId") Long userId);
}