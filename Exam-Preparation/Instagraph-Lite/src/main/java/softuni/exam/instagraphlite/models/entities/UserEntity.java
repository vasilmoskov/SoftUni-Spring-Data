package softuni.exam.instagraphlite.models.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Comparator;
import java.util.List;

@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity{
    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @ManyToOne(optional = false)
    private PictureEntity profilePicture;

    @OneToMany(mappedBy = "user")
    private List<PostEntity> posts;

    public UserEntity() {
    }

    public String getUsername() {
        return username;
    }

    public UserEntity setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserEntity setPassword(String password) {
        this.password = password;
        return this;
    }

    public PictureEntity getProfilePicture() {
        return profilePicture;
    }

    public UserEntity setProfilePicture(PictureEntity profilePicture) {
        this.profilePicture = profilePicture;
        return this;
    }

    public List<PostEntity> getPosts() {
        return posts;
    }

    public UserEntity setPosts(List<PostEntity> posts) {
        this.posts = posts;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder
                .append("User: ")
                .append(this.username)
                .append(System.lineSeparator())
                .append("Post count: ")
                .append(this.posts.size())
                .append(System.lineSeparator());

        this.posts.stream()
                .sorted(Comparator.comparing(p -> p.getPicture().getSize()))
                .forEach(p -> {
                    stringBuilder.append("==Post Details:")
                            .append(System.lineSeparator())
                            .append("----Caption: ")
                            .append(p.getCaption())
                            .append(System.lineSeparator())
                            .append("----Picture Size: ")
                            .append(String.format("%.2f", p.getPicture().getSize()))
                            .append(System.lineSeparator());
                });

        return stringBuilder.toString();
    }
}
