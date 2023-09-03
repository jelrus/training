package epam.com.esm.view.dto.response.impl.purchase;

import epam.com.esm.persistence.entity.impl.purchase.PurchaseData;
import epam.com.esm.persistence.entity.impl.purchase.type.Status;
import epam.com.esm.view.dto.response.DtoResponse;
import epam.com.esm.view.dto.response.impl.products.GiftCertificateDtoResponse;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * PurchaseDataDtoResponse is the data class, serves as purchase data values carrier in requests for select operations,
 * represents purchase data object for view
 */
public class PurchaseDataDtoResponse extends DtoResponse {

    /**
     * Holds name value
     */
    private LocalDateTime start;

    /**
     * Holds name value
     */
    private LocalDateTime end;

    /**
     * Holds name value
     */
    private Status status;

    /**
     * Holds gift certificate dto response
     */
    private GiftCertificateDtoResponse giftCertificate;

    /**
     * Constructs purchase data dto response from provided purchase data
     *
     * @param purchaseData provided purchase data
     * @param fold fold mode flag
     */
    public PurchaseDataDtoResponse(PurchaseData purchaseData, boolean fold) {
        super(purchaseData.getId());
        setStart(purchaseData.getStart());
        setEnd(purchaseData.getEnd());
        setStatus(purchaseData.getStatus());

        if (!fold) {
            setGiftCertificate(new GiftCertificateDtoResponse(purchaseData.getGiftCertificate()));
        }
    }

    /**
     * Gets value from start field
     *
     * @return {@code LocalDateTime} start value
     */
    public LocalDateTime getStart() {
        return start;
    }

    /**
     * Sets new value to start field
     *
     * @param start value for setting
     */
    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    /**
     * Gets value from end field
     *
     * @return {@code LocalDateTime} end value
     */
    public LocalDateTime getEnd() {
        return end;
    }

    /**
     * Sets new value to end field
     *
     * @param end value for setting
     */
    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    /**
     * Gets value from status field
     *
     * @return {@code Status} status value
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Sets new value to status field
     *
     * @param status value for setting
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * Gets gift certificate dto response
     *
     * @return {@code GiftCertificateDtoResponse} gift certificate dto response
     */
    public GiftCertificateDtoResponse getGiftCertificate() {
        return giftCertificate;
    }

    /**
     * Sets new gift certificate dto response
     *
     * @param giftCertificateDtoResponse gift certificate dto response
     */
    public void setGiftCertificate(GiftCertificateDtoResponse giftCertificateDtoResponse) {
        this.giftCertificate = giftCertificateDtoResponse;
    }

    /**
     * Compares source object and target object for equality
     *
     * @param o target object
     * @return {@code true} if objects are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PurchaseDataDtoResponse that = (PurchaseDataDtoResponse) o;
        return Objects.equals(start, that.start)
               && Objects.equals(end, that.end)
               && status == that.status
               && Objects.equals(giftCertificate, that.giftCertificate);
    }

    /**
     * Calculates hash for source object
     *
     * @return {@code int} hashcode integer value
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), start, end, status, giftCertificate);
    }
}