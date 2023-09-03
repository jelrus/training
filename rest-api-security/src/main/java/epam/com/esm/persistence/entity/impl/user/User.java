package epam.com.esm.persistence.entity.impl.user;

import epam.com.esm.persistence.entity.BaseEntity;
import epam.com.esm.persistence.entity.impl.action.Order;
import epam.com.esm.persistence.entity.impl.purchase.PurchaseData;
import epam.com.esm.persistence.entity.impl.user.component.Role;
import epam.com.esm.utils.search.filter.annotations.FilterObject;
import epam.com.esm.utils.search.filter.annotations.FilterParameter;
import epam.com.esm.utils.search.filter.annotations.FilterPrefix;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

/**
 * User is the data class, serves as the model for User object
 */
@Entity
@Audited
@Table(name = "user")
@FilterPrefix(prefix = "u.")
public class User extends BaseEntity {

    /**
     * Holds username value
     */
    @Column(name = "username")
    @FilterParameter(fieldName = "username", alias = {"u.username", "username", "uUsername"})
    private String username;

    /**
     * Holds password value
     */
    @Column(name = "password")
    private String password;

    /**
     * Holds roles value
     */
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles;

    /**
     * Holds orders values
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @FilterObject(cls = Order.class, path = "orders")
    private List<Order> orders;

    /**
     * Holds purchaseData value
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<PurchaseData> purchaseData;

    /**
     * Constructs User with superclass fields
     */
    public User() {
        super();
    }

    /**
     * Gets value from username field
     *
     * @return {@code String} username value
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets new value to username field
     *
     * @param username value for setting
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets value from password field
     *
     * @return {@code String} password value
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets new value to password field
     *
     * @param password value for setting
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets value from orders field
     *
     * @return {@code List<Order>} orders value
     */
    public List<Order> getOrders() {
        return orders;
    }

    /**
     * Sets new value to orders field
     *
     * @param orders value for setting
     */
    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    /**
     * Gets value from purchaseData field
     *
     * @return {@code List<PurchaseData>} purchaseData value
     */
    public List<PurchaseData> getPurchaseData() {
        return purchaseData;
    }

    /**
     * Sets new value to purchaseData field
     *
     * @param purchaseData value for setting
     */
    public void setPurchaseData(List<PurchaseData> purchaseData) {
        this.purchaseData = purchaseData;
    }

    /**
     * Gets value from roles field
     *
     * @return {@code List<Role>} roles value
     */
    public List<Role> getRoles() {
        return roles;
    }

    /**
     * Sets new value to roles field
     *
     * @param roles value for setting
     */
    public void setRoles(List<Role> roles) {
        this.roles = roles;
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
        User user = (User) o;
        return Objects.equals(username, user.username)
               && Objects.equals(password, user.password)
               && Objects.equals(roles, user.roles)
               && Objects.equals(orders, user.orders)
               && Objects.equals(purchaseData, user.purchaseData);
    }

    /**
     * Calculates hash for source object
     *
     * @return {@code int} hashcode integer value
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), username, password, roles, orders, purchaseData);
    }
}