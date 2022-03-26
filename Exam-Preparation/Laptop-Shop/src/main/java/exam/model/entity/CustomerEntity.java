package exam.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "customers")
public class CustomerEntity extends BaseEntity{
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate registeredOn;
    private TownEntity town;

    @Column(name = "first_name", nullable = false)
    public String getFirstName() {
        return firstName;
    }

    public CustomerEntity setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    @Column(name = "last_name", nullable = false)
    public String getLastName() {
        return lastName;
    }

    public CustomerEntity setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    @Column(nullable = false, unique = true)
    public String getEmail() {
        return email;
    }

    public CustomerEntity setEmail(String email) {
        this.email = email;
        return this;
    }

    @Column(name = "registered_on", nullable = false)
    public LocalDate getRegisteredOn() {
        return registeredOn;
    }

    public CustomerEntity setRegisteredOn(LocalDate registeredOn) {
        this.registeredOn = registeredOn;
        return this;
    }

    @ManyToOne(optional = false)
    public TownEntity getTown() {
        return town;
    }

    public CustomerEntity setTown(TownEntity town) {
        this.town = town;
        return this;
    }
}
