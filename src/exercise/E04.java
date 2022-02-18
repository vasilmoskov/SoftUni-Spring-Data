package exercise;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.stream.Collectors;

public class E04 {
    private static Connection connection;

    public static void main(String[] args) throws SQLException {
        Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("password", "1234");

        connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/minions_db", properties);

        Scanner scanner = new Scanner(System.in);

        List<String> minionsData = Arrays.stream(scanner.nextLine()
                        .split(" "))
                .skip(1)
                .collect(Collectors.toList());

        String villainName = Arrays.stream(scanner.nextLine()
                        .split(" "))
                .skip(1)
                .findFirst()
                .orElse(null);

        String minionName = minionsData.get(0);
        int minionAge = Integer.parseInt(minionsData.get(1));
        String minionTown = minionsData.get(2);

        int townId = getIdByName("towns", minionTown);

        if (townId == 0) {
            insertTown(minionTown);
            System.out.printf("Town %s was added to the database.%n", minionTown);
            townId = getIdByName("towns", minionTown);
        }

        int villainId = getIdByName("villains", villainName);

        if (villainId == 0) {
            insertVillain(villainName);
            System.out.printf("Villain %s was added to the database.%n", villainName);
            villainId = getIdByName("villains", villainName);
        }

        int minionId = getIdByName("minions", minionName);

        if (minionId == 0) {
            insertMinion(minionName, minionAge, townId);
            minionId = getIdByName("minions", minionName);
        }

        addMinionToVillain(minionId, villainId);
        System.out.printf("Successfully added %s to be minion of %s%n", minionName, villainName);

        connection.close();
    }

    private static int getIdByName(String table, String name) throws SQLException {
        String query = String.format("select id from %s where name = ?;", table);

        PreparedStatement statement = connection.prepareStatement(query);

        statement.setString(1, name);

        ResultSet resultSet = statement.executeQuery();

        int id = 0;

        if (resultSet.next()) {
            id = resultSet.getInt("id");
        }

        return id;
    }

    private static void insertTown(String minionTown) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("insert into towns (name) values (?);");
        statement.setString(1, minionTown);
        statement.execute();
    }

    private static void insertVillain(String villainName) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("insert into villains " +
                "(name, evilness_factor) " +
                "values (?, 'evil');");

        statement.setString(1, villainName);

        statement.execute();
    }

    private static void insertMinion(String minionName, int minionAge, int townId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("insert into minions (name, age, town_id) \n" +
                "values (?, ?, ?);");

        statement.setString(1, minionName);
        statement.setInt(2, minionAge);
        statement.setInt(3, townId);

        statement.execute();
    }

    private static void addMinionToVillain(int minionId, int villainId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("insert into minions_villains values(?, ?);");
        statement.setInt(1, minionId);
        statement.setInt(2, villainId);
        statement.execute();
    }
}
