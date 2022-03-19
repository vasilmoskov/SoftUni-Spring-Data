package bg.softuni.json_processing_exercise.product_shop.models.dto;

import com.google.gson.annotations.Expose;

import java.util.List;

public class TotalUsersWithSoldProductsDto {
    @Expose
    private Integer usersCount;
    @Expose
    private List<UserWithSoldProductsDto2> users;

    public TotalUsersWithSoldProductsDto() {
    }

    public Integer getUsersCount() {
        return usersCount;
    }

    public void setUsersCount(Integer usersCount) {
        this.usersCount = usersCount;
    }

    public List<UserWithSoldProductsDto2> getUsers() {
        return users;
    }

    public void setUsers(List<UserWithSoldProductsDto2> users) {
        this.users = users;
    }
}
