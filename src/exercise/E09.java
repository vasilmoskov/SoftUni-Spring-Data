package exercise;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class E09 {
    public static Connection connection;

    public static void main(String[] args) throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/minions_db", "root", "1234");

        Scanner scanner = new Scanner(System.in);
        int id = Integer.parseInt(scanner.nextLine());

        increaseAgeById(id);
        printNameAndAge();
    }

    private static void increaseAgeById(int id) throws SQLException {
        CallableStatement statement = connection.prepareCall("call usp_get_older(?);");
        statement.setInt(1, id);
        statement.execute();
    }

    private static void printNameAndAge() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select name, age from minions;");

        while (resultSet.next()) {
            System.out.println(resultSet.getString("name") + " " + resultSet.getInt("age"));
        }
    }
}
