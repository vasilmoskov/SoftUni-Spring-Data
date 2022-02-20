import entities.User;
import orm.EntityManager;
import orm.MyConnector;

import java.sql.Connection;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) throws Exception {
        MyConnector.createConnection("root", "1234", "test");

        Connection connection = MyConnector.getConnection();
        EntityManager<User> entityManager = new EntityManager<>(connection);

        entityManager.doCreate(User.class);


        User user = new User("Kolyo", 50, LocalDate.of(2021, 6, 20));

        entityManager.doAlter(user);
//
        User first = entityManager.findFirst(User.class, "id = 1");
        first.setId(1);
        first.setAge(25);
        user.setUsername("Gosho");

        entityManager.persist(first);

        Iterable<User> users = entityManager.find(User.class, "age > 30");
        for (User u : users) {
            System.out.println(u);
        }

        User found2 = entityManager.findFirst(User.class, "age > 45");
        System.out.println(found2);

        entityManager.delete(User.class, "id = 1");
    }
}
