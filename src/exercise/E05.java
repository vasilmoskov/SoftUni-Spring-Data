package exercise;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class E05 {
    private static Connection connection;
    public static void main(String[] args) throws SQLException {

        connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/minions_db", "root", "1234");

        Scanner scanner = new Scanner(System.in);
        String country = scanner.nextLine();

        int updated = getUpdatedCount(country);

        if (updated > 0) {
            List<String> towns = getTownsNamesByCountry(country);
            System.out.println(updated + " town names were affected.");
            System.out.printf("[%s]", String.join(", ", towns));
        } else {
            System.out.println("No town names were affected");
        }

        connection.close();
    }

    private static List<String> getTownsNamesByCountry(String country) throws SQLException {
        List<String> towns = new ArrayList<>();

        PreparedStatement statement = connection.prepareStatement("select name from towns where country = ?");
        statement.setString(1, country);

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            towns.add(resultSet.getString("name"));
        }

        return towns;
    }

    private static int getUpdatedCount(String country) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("update towns\n" +
                "set name = UPPER(name)\n" +
                "where country = ?;");
        statement.setString(1, country);

        return statement.executeUpdate();
    }
}
