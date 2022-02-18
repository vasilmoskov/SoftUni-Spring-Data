package lab;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter username default (root): ");
        String user = sc.nextLine();
        user = user.equals("") ? "root" : user;

        System.out.print("Enter password default (empty):");
        String password = sc.nextLine().trim();

        Properties props = new Properties();
        props.setProperty("user", user);
        props.setProperty("password", password);

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/soft_uni", props);

        PreparedStatement statement = connection.prepareStatement("SELECT * from employees where salary > ?");

        System.out.println("Enter a minimum salary:");
        String salary = sc.nextLine();
        statement.setDouble(1, Double.parseDouble(salary));
        ResultSet rs = statement.executeQuery();

        while (rs.next()) {
            System.out.printf("%s %s", rs.getString("first_name"), rs.getString("last_name"));
            System.out.println();
        }
    }
}
