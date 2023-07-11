package epam.com.esm.model.dao.impl.purchase;

import epam.com.esm.persistence.entity.impl.action.Order;
import epam.com.esm.persistence.entity.impl.products.GiftCertificate;
import epam.com.esm.persistence.entity.impl.purchase.PurchaseData;
import epam.com.esm.persistence.entity.impl.purchase.type.Status;
import epam.com.esm.persistence.entity.impl.user.User;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.List;

/**
 * PurchaseDataDao class is the service class, used for generating purchase data on order create
 */
@Service
@Transactional
public class PurchaseDataDao {

    /**
     * Holds EntityManager object, which used to interact with the persistence context
     */
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Generates purchase data for User
     *
     * @param o requested order
     * @param u requested user
     */
    public void generatePurchaseData(Order o, User u) {
        for (GiftCertificate gc: o.getGiftCertificates()) {
            PurchaseData pd = new PurchaseData();
            pd.setStart(o.getPurchaseDate());
            pd.setEnd(o.getPurchaseDate().plusDays(gc.getDuration()));
            pd.setStatus(Status.ACTIVE);
            gc.getPurchaseData().add(pd);
            pd.setGiftCertificate(gc);
            pd.setUser(u);
            u.getPurchaseData().add(entityManager.merge(pd));
        }
    }

    /**
     * Checks if purchased gift certificate purchase is still active by delay
     * If purchase isn't active anymore sets expired status on purchase
     */
    @Scheduled(fixedDelay = 5*60*1000)
    public void changeStatus() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<PurchaseData> cq = cb.createQuery(PurchaseData.class);
        Root<PurchaseData> rootEntry = cq.from(PurchaseData.class);
        List<PurchaseData> purchaseData = entityManager.createQuery(cq.select(rootEntry)).getResultList();

        for (PurchaseData pd: purchaseData) {
            if (LocalDateTime.now().isAfter(pd.getEnd()) && pd.getStatus().equals(Status.ACTIVE)) {
                pd.setStatus(Status.EXPIRED);
                entityManager.merge(pd);
            }
        }
    }
}