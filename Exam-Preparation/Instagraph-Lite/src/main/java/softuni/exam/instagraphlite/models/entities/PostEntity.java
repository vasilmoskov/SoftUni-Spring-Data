package softuni.exam.instagraphlite.models.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "posts")
public class PostEntity extends BaseEntity{
    @Column(nullable = false)
    private String caption;

    @ManyToOne(optional = false)
    private UserEntity user;

    @ManyToOne(optional = false)
    private PictureEntity picture;

    public PostEntity() {
    }

    public String getCaption() {
        return caption;
    }

    public PostEntity setCaption(String caption) {
        this.caption = caption;
        return this;
    }

    public UserEntity getUser() {
        return user;
    }

    public PostEntity setUser(UserEntity user) {
        this.user = user;
        return this;
    }

    public PictureEntity getPicture() {
        return picture;
    }

    public PostEntity setPicture(PictureEntity picture) {
        this.picture = picture;
        return this;
    }
}
