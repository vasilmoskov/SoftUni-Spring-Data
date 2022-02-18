package exercise;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class E06 {

    /* TODO:
    select count(villain_id) from minions_villains where villain_id = 3;
    select name from villains where id = 1;
    delete from minions_villains where villain_id = 3;
    delete from villains where id = 3;
     */

    private static Connection connection;

    public static void main(String[] args) throws SQLException {

        Scanner scanner = new Scanner(System.in);

        int id = Integer.parseInt(scanner.nextLine());

        connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/minions_db", "root", "1234");

        try {
            connection.setAutoCommit(false);

            String name = getNameById(id);

            int minionsCount = getMinionsCountByVillainId(id);

            deleteFromTableById("minions_villains", "villain_id", id);
            deleteFromTableById("villains", "id", id);

            connection.commit();

            if (minionsCount > 0) {
                System.out.println(name + " was deleted");
                System.out.println(minionsCount + " minions were released");
            } else {
                System.out.println("No such villain was found");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
        }
    }

    private static void deleteFromTableById(String table, String whereColumn, int id) throws SQLException {
        String query = String.format("delete from %s where %s = ?;",table, whereColumn );

        PreparedStatement statement = connection.prepareStatement(query);

        statement.setInt(1, id);

        statement.executeUpdate();
    }

    private static int getMinionsCountByVillainId(int id) throws SQLException {
        PreparedStatement statement = connection
                .prepareStatement("select count(villain_id) as minions_count from minions_villains where villain_id = ?;");

        statement.setInt(1, id);

        ResultSet resultSet = statement.executeQuery();

//        int count = 0;
//
//        if (resultSet.next()) {
//            count = resultSet.getInt("minions_count");
//        }
//        return count;

        resultSet.next();
        int count = resultSet.getInt("minions_count");
        return count;
    }

    private static String getNameById(int id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("select name from villains where id = ?;");

        statement.setInt(1, id);

        ResultSet resultSet = statement.executeQuery();

        String name = null;

        if (resultSet.next()) {
            name = resultSet.getString("name");
        }

        return name;
    }
}
