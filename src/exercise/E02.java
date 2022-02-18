package exercise;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class E02 {
    public static void main(String[] args) throws SQLException {

        Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("password", "1234");

        Connection connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/minions_db", properties);

        // Exercise 2
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select v.name, count(distinct mv.minion_id) as number_of_minions\n" +
                "from villains as v\n" +
                "join minions_villains as mv on v.id = mv.villain_id\n" +
                "group by v.name\n" +
                "having number_of_minions > 15\n" +
                "order by number_of_minions desc;");

        /*
        PreparedStatement preparedStatement = connection.prepareStatement("select name, count(mv.villain_id) as number_of_minions\n" +
        "from villains as v\n" +
        "join minions_villains as mv on v.id = mv.villain_id\n" +
        "group by mv.villain_id\n" +
        "having number_of_minions > 15\n" +
        "order by number_of_minions desc;");

        ResultSet resultSet = preparedStatement.executeQuery();
        */

        while (resultSet.next()) {
            String name = resultSet.getString(1); // get column by index
            int numberOfMinions = resultSet.getInt("number_of_minions"); // get column by column name

            System.out.println(name + " " + numberOfMinions);
        }

        connection.close();
    }
}
