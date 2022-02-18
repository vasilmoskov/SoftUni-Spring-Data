package lab;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Diablo {
    public static void main(String[] args) throws SQLException {

        Scanner scanner = new Scanner(System.in);

        Connection connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/diablo", "root", "1234");

        PreparedStatement statement = connection.prepareStatement(
                "select first_name, last_name, count(user_id) as games_played\n" +
                        "from users_games as ug\n" +
                        "join users as u\n" +
                        "on u.id = ug.user_id\n" +
                        "where u.user_name= ?" +
                        "group by user_id;"
        );

        System.out.println("Enter username");
        String username = scanner.nextLine();

        statement.setString(1, username);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            System.out.println("User: " + username);
            System.out.printf("%s %s has played %d games%n",
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name"),
                    resultSet.getInt("games_played"));
        } else {
            System.out.println("No such user exists");
        }
    }
}
