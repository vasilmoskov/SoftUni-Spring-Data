package exercise;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class E07 {
    private static Connection connection;

    public static void main(String[] args) throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/minions_db", "root", "1234");

        List<String> names = getAllNames();

        for (int i = 0; i < names.size() / 2; i++) {
            System.out.println(names.get(i));
            System.out.println(names.get(names.size() - 1 - i));
        }

        if (names.size() % 2 != 0) {
            System.out.println(names.get(names.size() / 2));
        }
    }

    private static List<String> getAllNames() throws SQLException {
        List<String> names = new ArrayList<>();

        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("select name from minions;");

        while (resultSet.next()) {
            names.add(resultSet.getString("name"));
        }

        return names;
    }
}
