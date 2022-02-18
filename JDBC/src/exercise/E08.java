package exercise;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Scanner;

public class E08 {
    public static Connection connection;

    public static void main(String[] args) throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/minions_db", "root", "1234");

        Scanner scanner = new Scanner(System.in);
        int[] ids = Arrays.stream(scanner.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        for (int id : ids) {
            updateById(id);
        }

        printNameAndAge();
    }

    private static void updateById(int id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("update minions\n" +
                "set age = age + 1, name = lower(name)\n" +
                "where id = ?;");

        statement.setInt(1, id);

        statement.executeUpdate();
    }

    private static void printNameAndAge() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select name, age from minions;");

        while (resultSet.next()) {
            System.out.println(resultSet.getString("name") + " " + resultSet.getInt("age"));
        }
    }
}
