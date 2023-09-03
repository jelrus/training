package epam.com.esm.view.dto.response.impl.action;

import epam.com.esm.persistence.entity.impl.action.Order;
import epam.com.esm.view.dto.response.DtoResponse;
import epam.com.esm.view.dto.response.impl.products.GiftCertificateDtoResponse;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * OrderDtoResponse is the data class, serves as order's values carrier in requests for select operations,
 * represents full object for view
 */
public class OrderDtoResponse extends DtoResponse {

    /**
     * Holds cost value
     */
    private BigDecimal cost;

    /**
     * Holds purchaseDate value
     */
    private LocalDateTime purchaseDate;

    /**
     * Holds gift certificates dto responses
     */
    private List<GiftCertificateDtoResponse> giftCertificates;

    /**
     * Constructs order dto response from provided order
     *
     * @param order provided order
     */
    public OrderDtoResponse(Order order) {
        super(order.getId());
        setCost(order.getCost());
        setPurchaseDate(order.getPurchaseDate());
    }

    /**
     * Gets value from cost field
     *
     * @return {@code BigDecimal} cost value
     */
    public BigDecimal getCost() {
        return cost;
    }

    /**
     * Sets new value to cost field
     *
     * @param cost value for setting
     */
    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    /**
     * Gets value from purchaseDate field
     *
     * @return {@code LocalDateTime} purchaseDate value
     */
    public LocalDateTime getPurchaseDate() {
        return purchaseDate;
    }

    /**
     * Sets new value to purchaseDate field
     *
     * @param purchaseDate value for setting
     */
    public void setPurchaseDate(LocalDateTime purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    /**
     * Gets gift certificates dto responses
     *
     * @return {@code List<GiftCertificateDtoResponse>} gift certificates dto responses
     */
    public List<GiftCertificateDtoResponse> getGiftCertificates() {
        return giftCertificates;
    }

    /**
     * Sets new gift certificates dto responses with name only
     *
     * @param giftCertificates gift certificates dto responses
     */
    public void setGiftCertificates(List<GiftCertificateDtoResponse> giftCertificates) {
        this.giftCertificates = giftCertificates;
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
        OrderDtoResponse that = (OrderDtoResponse) o;
        return Objects.equals(cost, that.cost)
               && Objects.equals(purchaseDate, that.purchaseDate)
               && Objects.equals(giftCertificates, that.giftCertificates);
    }

    /**
     * Calculates hash for source object
     *
     * @return {@code int} hashcode integer value
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), cost, purchaseDate, giftCertificates);
    }
}