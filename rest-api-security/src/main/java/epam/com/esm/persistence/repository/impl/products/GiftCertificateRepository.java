package epam.com.esm.persistence.repository.impl.products;

import epam.com.esm.persistence.entity.impl.products.GiftCertificate;
import epam.com.esm.persistence.repository.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * GiftCertificateRepository is the repository, provides jpa operations contracts for gift certificate
 */
@Repository
public interface GiftCertificateRepository extends BaseRepository<GiftCertificate> {

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
     * @return {@code Optional<GiftCertificate>} result of finding
     */
    Optional<GiftCertificate> findByName(String name);
}