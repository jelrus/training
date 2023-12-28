package epam.com.esm.model.service.impl.purchase;

import epam.com.esm.exception.types.EmptyOrderException;
import epam.com.esm.exception.types.InputException;
import epam.com.esm.exception.types.NotFoundException;
import epam.com.esm.persistence.entity.impl.action.Order;
import epam.com.esm.persistence.entity.impl.products.GiftCertificate;
import epam.com.esm.persistence.entity.impl.purchase.PurchaseData;
import epam.com.esm.persistence.entity.impl.purchase.type.Status;
import epam.com.esm.persistence.repository.impl.products.GiftCertificateRepository;
import epam.com.esm.persistence.repository.impl.purchase.PurchaseDataRepository;
import epam.com.esm.persistence.repository.impl.user.UserRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * PurchaseService is the service class, contains operations related with order creating and andjusting
 */
@Service
@Transactional
public class PurchaseService {

    /**
     * Holds GiftCertificateRepository object
     */
    private final GiftCertificateRepository gcRepo;

    /**
     * Holds UserRepository object
     */
    private final UserRepository uRepo;

    /**
     * Holds PurchaseDataRepository object
     */
    private final PurchaseDataRepository pdRepo;

    /**
     * Constructs PurchaseDataService with GiftCertificateRepository, PurchaseDataRepository and UserRepository objects
     *
     * @param gcRepo repository, provides jpa operations for gift certificate
     * @param uRepo  repository, provides jpa operations for user
     * @param pdRepo repository, provides jpa operations for purchase data
     */
    public PurchaseService(GiftCertificateRepository gcRepo, UserRepository uRepo, PurchaseDataRepository pdRepo) {
        this.gcRepo = gcRepo;
        this.uRepo = uRepo;
        this.pdRepo = pdRepo;
    }

    /**
     * Assembles order for user
     *
     * @param o requested order
     */
    public void assembleOrder(Order o) {
        adjustUser(o);
        adjustOrder(o);
        adjustCost(o);
        generatePurchaseData(o);
    }

    /**
     * Checks if purchased gift certificate purchase is still active by delay
     * If purchase isn't active anymore sets expired status on purchase
     */
    @Scheduled(fixedDelay = 5*60*1000)
    public void changeStatus() {
        pdRepo.findAll().stream().filter(filterExpired()).forEach(expire());
    }

    /**
     * Generates purchase data for User and Gift Certificates
     *
     * @param order requested order
     */
    private void generatePurchaseData(Order order) {
        for (GiftCertificate gc: order.getGiftCertificates()) {
            PurchaseData pd = new PurchaseData();
            pd.setStart(order.getPurchaseDate());
            pd.setEnd(order.getPurchaseDate().plusDays(gc.getDuration()));
            pd.setStatus(Status.ACTIVE);
            pd.setGiftCertificate(gc);
            gc.getPurchaseData().add(pd);
            pd.setUser(order.getUser());
            order.getUser().getPurchaseData().add(pd);
            pdRepo.save(pd);
        }
    }

    /**
     * Supplementary method, adjusts user for order before create/update operations
     *
     * @param order provided order object
     */
    private void adjustUser(Order order) {
        checkForNullUser(order);
        checkForUserExistence(order);
        order.setUser(uRepo.findByUsername(order.getUser().getUsername()).orElseThrow(
                () -> new NotFoundException("User with (name = " + order.getUser().getUsername() + ") not found")
        ));
    }

    /**
     * Supplementary method, adjusts order before create/update operations
     *
     * @param order provided order object
     */
    private void adjustOrder(Order order) {
        checkGiftCertificatesNullOrEmpty(order);
        order.setPurchaseDate(LocalDateTime.now());
        adjustGiftCertificates(order);
        adjustCost(order);
    }

    /**
     * Supplementary method, checks user for nullity
     * Will throw InputException if user in order is null
     *
     * @param order provided order object
     */
    private void checkForNullUser(Order order) {
        if (order.getUser() == null) {
            throw new InputException("Response failed due unexpected input");
        }
    }

    /**
     * Supplementary method, checks user existence
     * Will throw NotFoundException if user wasn't found
     *
     * @param order provided order object
     */
    private void checkForUserExistence(Order order) {
        if (!uRepo.existsByUsername(order.getUser().getUsername())) {
            throw new NotFoundException("User with (username = " + order.getUser().getUsername() + ") not found");
        }
    }

    /**
     * Supplementary method, checks order's gift certificates collection on nullity and emptiness
     * Will throw EmptyOrderException if collection is null or empty
     *
     * @param order provided order object
     */
    private void checkGiftCertificatesNullOrEmpty(Order order) {
        if (order.getGiftCertificates() == null || order.getGiftCertificates().isEmpty()) {
            throw new EmptyOrderException("Order with (id = " + order.getId() + " is empty");
        }
    }

    /**
     * Supplementary method, adjusts gift certificates for order in methods where full order object is required
     *
     * @param order provided order object
     */
    private void adjustGiftCertificates(Order order) {
        order.setGiftCertificates(
                order.getGiftCertificates().stream().map(refineGiftCertificate())
                        .collect(Collectors.toCollection(ArrayList::new))
        );
    }

    /**
     * Supplementary method, converts requested gift certificate with partial values to full object
     *
     * @return {@code Function<GiftCertificate, GiftCertificate>} function for converting
     */
    private Function<GiftCertificate, GiftCertificate> refineGiftCertificate() {
        return gc -> {
            checkForNullGiftCertificate(gc);
            checkForGiftCertificateExistence(gc);
            return gcRepo.findByName(gc.getName()).orElseThrow(
                    () -> new NotFoundException("Gift Certificate with (name = " + gc.getName() + " not found")
            );
        };
    }

    /**
     * Supplementary method, checks gift certificate for nullity
     * Will throw InputException if user in order is null
     *
     * @param giftCertificate provided gift certificate object
     */
    private void checkForNullGiftCertificate(GiftCertificate giftCertificate) {
        if (giftCertificate == null) {
            throw new InputException("Response failed due unexpected input");
        }
    }

    /**
     * Supplementary method, checks gift certificate existence
     * Will throw NotFoundException if gift certificate wasn't found
     *
     * @param giftCertificate provided gift certificate object
     */
    private void checkForGiftCertificateExistence(GiftCertificate giftCertificate) {
        if (!gcRepo.existsByName(giftCertificate.getName())) {
            throw new NotFoundException("Gift Certificate with (name = " + giftCertificate.getName() + ") not found");
        }
    }

    /**
     * Supplementary method, calculates order's cost
     *
     * @param order provided order object
     */
    private void adjustCost(Order order) {
        order.setCost(
                order.getGiftCertificates().stream().map(GiftCertificate::getPrice)
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
        );
    }

    /**
     * Filters expired purchases
     *
     * @return {@code Predicate<PurchaseData>} condition for expiration setting
     */
    private Predicate<PurchaseData> filterExpired()  {
        return pd -> LocalDateTime.now().isAfter(pd.getEnd()) && pd.getStatus().equals(Status.ACTIVE);
    }

    /**
     * Sets expired status for purchase
     *
     * @return {@code Consumer<PurchaseData>} function for setting expiration status
     */
    private Consumer<PurchaseData> expire() {
        return pd -> {
            pd.setStatus(Status.EXPIRED);
            pdRepo.save(pd);
        };
    }
}