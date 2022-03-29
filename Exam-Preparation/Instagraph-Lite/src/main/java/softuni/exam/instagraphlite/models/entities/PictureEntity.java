package softuni.exam.instagraphlite.models.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "pictures")
public class PictureEntity extends BaseEntity{

    @Column(nullable = false, unique = true)
    private String path;

    @Column(nullable = false)
    private Double size;

    public PictureEntity() {
    }

    public String getPath() {
        return path;
    }

    public PictureEntity setPath(String path) {
        this.path = path;
        return this;
    }

    public Double getSize() {
        return size;
    }

    public PictureEntity setSize(Double size) {
        this.size = size;
        return this;
    }

    @Override
    public String toString() {
        return String.format("%.2f - %s", size, path);
    }
}
