package exercise;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

public class E03 {
    public static void main(String[] args) throws SQLException {

        Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("password", "1234");

        Connection connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/minions_db", properties);

        PreparedStatement statement = connection.prepareStatement("select m.name, m.age, v.name\n" +
                "from minions as m\n" +
                "join minions_villains mv on m.id = mv.minion_id\n" +
                "join villains v on mv.villain_id = v.id\n" +
                "where mv.villain_id = ?;");

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter villain id:");
        int id = Integer.parseInt(scanner.nextLine());

        statement.setInt(1, id);

        ResultSet resultSet = statement.executeQuery();

        int row = 1;

        while (resultSet.next()) {
            if (row == 1) {
                System.out.println("Villain: " + resultSet.getString("v.name"));
            }

            System.out.printf("%d. %s %d%n",
                    row++, resultSet.getString("m.name"), resultSet.getInt("m.age"));
        }

        if (row == 1) {
            System.out.printf("No villain with ID %d exists in the database.", id);
        }

        connection.close();
    }
}
