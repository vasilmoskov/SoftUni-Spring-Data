package softuni.exam.models.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "passengers")
public class PassengerEntity extends BaseEntity {
    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private Integer age;

    @Column
    private String phoneNumber;

    @Column(unique = true)
    private String email;

    @ManyToOne
    private TownEntity town;

    @OneToMany(mappedBy = "passenger")
    private List<TicketEntity> tickets;

    public String getFirstName() {
        return firstName;
    }

    public PassengerEntity setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public PassengerEntity setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public Integer getAge() {
        return age;
    }

    public PassengerEntity setAge(Integer age) {
        this.age = age;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public PassengerEntity setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public PassengerEntity setEmail(String email) {
        this.email = email;
        return this;
    }

    public TownEntity getTown() {
        return town;
    }

    public PassengerEntity setTown(TownEntity town) {
        this.town = town;
        return this;
    }

    public List<TicketEntity> getTickets() {
        return tickets;
    }

    public PassengerEntity setTickets(List<TicketEntity> tickets) {
        this.tickets = tickets;
        return this;
    }

    @Override
    public String toString() {
        return String.format("Passenger %s %s" +
                "\n\tEmail - %s" +
                "\n\tPhone - %s" +
                "\n\tNumber of tickets - %d", firstName, lastName, email, phoneNumber, tickets.size());
    }
}
