package epam.com.esm.persistence.entity.impl.user.component;

import epam.com.esm.persistence.entity.BaseEntity;
import epam.com.esm.persistence.entity.impl.user.User;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

/**
 * Role is the data class, serves as the model for Role object
 */
@Entity
@Audited
@Table(name = "roles")
public class Role extends BaseEntity {

    /**
     * Holds name value
     */
    @Column(name = "name")
    private String name;

    /**
     * Holds users value
     */
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> users;

    /**
     * Constructs Role with superclass fields
     */
    public Role() {
        super();
    }

    /**
     * Constructs Role with superclass fields and name
     */
    public Role(String name) {
        super();
        this.name = name;
    }

    /**
     * Gets value from name field
     *
     * @return {@code String} name value
     */
    public String getName() {
        return name;
    }

    /**
     * Sets new value to name field
     *
     * @param name value for setting
     */
    public void setName(String name) {
        this.name = name;
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
        Role role = (Role) o;
        return Objects.equals(name, role.name) && Objects.equals(users, role.users);
    }

    /**
     * Calculates hash for source object
     *
     * @return {@code int} hashcode integer value
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, users);
    }
}