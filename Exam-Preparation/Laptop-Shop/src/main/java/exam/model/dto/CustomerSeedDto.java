package exam.model.dto;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

public class CustomerSeedDto {
    @Expose
    @Size(min = 2)
    private String firstName;

    @Expose
    @Size(min = 2)
    private String lastName;

    @Expose
    @Email
    private String email;

    @Expose
    private String registeredOn;

    @Expose
    private JsonNameDto town;

    public CustomerSeedDto() {
    }

    public String getFirstName() {
        return firstName;
    }

    public CustomerSeedDto setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public CustomerSeedDto setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public CustomerSeedDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getRegisteredOn() {
        return registeredOn;
    }

    public CustomerSeedDto setRegisteredOn(String registeredOn) {
        this.registeredOn = registeredOn;
        return this;
    }

    public JsonNameDto getTown() {
        return town;
    }

    public CustomerSeedDto setTown(JsonNameDto town) {
        this.town = town;
        return this;
    }
}
