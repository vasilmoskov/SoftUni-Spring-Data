package softuni.exam.instagraphlite.models.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class PostDto {

    @XmlElement
    @Size(min = 21)
    private String caption;

    @XmlElement
    @NotNull
    private UserDto user;

    @XmlElement
    @NotNull
    private PictureDto picture;

    public PostDto() {
    }

    public String getCaption() {
        return caption;
    }

    public PostDto setCaption(String caption) {
        this.caption = caption;
        return this;
    }

    public UserDto getUser() {
        return user;
    }

    public PostDto setUser(UserDto user) {
        this.user = user;
        return this;
    }

    public PictureDto getPicture() {
        return picture;
    }

    public PostDto setPicture(PictureDto picture) {
        this.picture = picture;
        return this;
    }
}
