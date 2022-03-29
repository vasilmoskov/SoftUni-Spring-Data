package softuni.exam.instagraphlite.models.dto;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserSeedDto {
    @Expose
    @NotNull
    @Size(min = 2, max = 18)
    private String username;

    @Expose
    @NotNull
    @Size(min = 4)
    private String password;

    @Expose
    @NotNull
    private String profilePicture;

    public String getUsername() {
        return username;
    }

    public UserSeedDto setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserSeedDto setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public UserSeedDto setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
        return this;
    }
}
