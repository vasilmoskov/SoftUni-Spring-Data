package orm;

import orm.annotation.Column;
import orm.annotation.Entity;
import orm.annotation.Id;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EntityManager<E> implements DbContext<E> {
    private Connection connection;

    public EntityManager(Connection connection) {
        this.connection = connection;
    }

    private Field getId(Class<?> entity) {
        return Arrays.stream(entity.getDeclaredFields())
                .filter(x -> x.isAnnotationPresent(Id.class))
                .findFirst()
                .orElseThrow(() -> new UnsupportedOperationException("Entity does not have primary key"));
    }

    private String getTableName(Class<?> entity) {
        return Arrays.stream(entity.getAnnotations())
                .filter(a -> a instanceof Entity)
                .map(a -> (Entity) a)
                .map(Entity::name)
                .findFirst()
                .orElse(null);
    }

    private String getTableName(E entity) {
        return Arrays.stream(entity.getClass().getAnnotations())
                .filter(a -> a instanceof Entity)
                .map(a -> (Entity) a)
                .map(Entity::name)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void doCreate(Class<E> entityClass) throws SQLException {
        String tableName = getTableNameFromEntity(entityClass);
        String query = String.format("CREATE TABLE %s ( id INT PRIMARY KEY AUTO_INCREMENT, %s);",
                tableName, getAllFieldsAndDataTypes(entityClass));

        PreparedStatement statement = connection.prepareStatement(query);
        statement.execute();
    }

    @Override
    public void doAlter(E entity) throws SQLException {
        String tableName = getTableName(entity);

        String query = String.format("ALTER TABLE %s ADD COLUMN %s;", tableName, getNewFields(entity.getClass()));

        connection.prepareStatement(query).executeUpdate();
    }

    private String getNewFields(Class<?> entityClass) throws SQLException {
        StringBuilder sb = new StringBuilder();
        Set<String> allFields = getAllFieldsFromTable();

        Arrays.stream(entityClass.getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(Column.class))
                .forEach(f -> {
                    if (!allFields.contains(f.getName())) {
                        sb.append(getNameAndDateTypeOfField(f));
                    }
                });

        return sb.toString();
    }

    private String getNameAndDateTypeOfField(Field field) {
        field.setAccessible(true);

        String name = field.getAnnotation(Column.class).name();
        String dbType = getDbType(field);

        return name + " " + dbType;
    }


    private Set<String> getAllFieldsFromTable() throws SQLException {
        Set<String> set = new HashSet<>();

        PreparedStatement statement = connection.prepareStatement("select column_name from information_schema.COLUMNS\n" +
                "where TABLE_SCHEMA='test' and COLUMN_NAME != 'id' and TABLE_NAME='users';");

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            set.add(resultSet.getString(1));
        }

        return set;
    }

    private String getAllFieldsAndDataTypes(Class<E> entityClass) {
        StringBuilder sb = new StringBuilder();

        Field[] fields = entityClass.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);

            if (field.isAnnotationPresent(Column.class)) {

                String name = field.getName();
                String dbType = getDbType(field);

                sb.append(name).append(" ").append(dbType).append(", ");
            }
        }

        return sb.substring(0, sb.length() - 1);
    }

    private String getDbType(Field field) {
        Class<?> type = field.getType();

        if (type == int.class || type == long.class) {
            return "INT";
        } else if (type == double.class) {
            return "DECIMAL(19,4)";
        } else if (type == LocalDate.class) {
            return "DATE";
        } else {
            return "VARCHAR(255)";
        }
    }

    @Override
    public boolean persist(E entity) throws IllegalAccessException, SQLException {
        Field primaryKey = getId(entity.getClass());
        primaryKey.setAccessible(true);
        Object value = primaryKey.get(entity);

        if (value == null || (long) value <= 0) {
            return doInsert(entity, primaryKey);
        }

        return doUpdate(entity, primaryKey);
    }

    private boolean doInsert(E entity, Field primaryKey) throws SQLException {
        String tableName = this.getTableName(entity.getClass());
        String query = "INSERT INTO " + tableName + " (";

        List<String> columnNames = new ArrayList<>();
        List<String> columnValues = new ArrayList<>();

        Arrays.stream(entity.getClass().getDeclaredFields()).forEach(f -> {
            f.setAccessible(true);
            Column column = f.getAnnotation(Column.class);

            if (column == null) {
                return;
            }

            String name = column.name();
            columnNames.add(name);

            try {
                Object value = f.get(entity);
                Class<?> type = f.getType();

                if (type != long.class && type != int.class) {
                    value = "'" + value + "'";
                }

                columnValues.add(value.toString());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });

        String collectedNames = String.join(", ", columnNames);
        String collectedValues = String.join(", ", columnValues);

        query += collectedNames + ") VALUES (" + collectedValues + ");";

        return connection.prepareStatement(query).execute();
    }

    private boolean doUpdate(E entity, Field primaryKey) throws SQLException, IllegalAccessException {
        StringBuilder query = new StringBuilder();

        query.append("UPDATE ")
                .append(this.getTableName(entity.getClass()))
                .append(" SET ");

        List<String> toUpdate = new ArrayList<>();

        Field[] fields = entity.getClass().getDeclaredFields();

        for (Field f : fields) {
            f.setAccessible(true);
            Column column = f.getAnnotation(Column.class);

            if (column == null) {
                continue;
            }

            String name = column.name();

            try {
                Object value = f.get(entity);
                Class<?> type = f.getType();

                if (type != long.class && type != int.class) {
                    value = "'" + value + "'";
                }

                toUpdate.add(String.format("%s=%s", name, value));

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        Object id = primaryKey.get(entity);

        query.append(String.join(", ", toUpdate))
                .append(" WHERE id = ")
                .append(id);

        return connection.prepareStatement(query.toString()).execute();
    }

    @Override
    public Iterable<E> find(Class<E> table) throws SQLException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        return find(table, null);
    }

    @Override
    public Iterable<E> find(Class<E> table, String where) throws SQLException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        List<E> entities = new ArrayList<>();

        Statement statement = connection.createStatement();
        String tableName = getTableNameFromEntity(table);

        String query = String.format("SELECT * FROM %s %s;",
                tableName, where != null ? "WHERE " + where : "");

        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()) {
            E entity = table.getDeclaredConstructor().newInstance();
            fillEntity(table, resultSet, entity);
            entities.add(entity);
        }

        return entities;
    }

    @Override
    public E findFirst(Class<E> table) throws SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        return findFirst(table, null);
    }

    @Override
    public E findFirst(Class<E> table, String where) throws SQLException, NoSuchMethodException,
            InvocationTargetException, InstantiationException, IllegalAccessException {

        Statement statement = connection.createStatement();
        String tableName = getTableNameFromEntity(table);

        String query = String.format("SELECT * FROM %s %s LIMIT 1;",
                tableName, where != null ? "WHERE " + where : "");

        ResultSet resultSet = statement.executeQuery(query);
        E entity = table.getDeclaredConstructor().newInstance();

        resultSet.next();
        fillEntity(table, resultSet, entity);

        return entity;
    }

    @Override
    public void delete(Class<E> table, String where) throws SQLException {
        String tableName = getTableNameFromEntity(table);

        String query = String.format("DELETE FROM %s WHERE %s;", tableName, where);

        connection.prepareStatement(query).execute();
    }

    private String getTableNameFromEntity(Class<E> table) {
        return getTableName(table);
    }

    private void fillEntity(Class<E> table, ResultSet resultSet, E entity) throws SQLException, IllegalAccessException {
        Field[] declaredFields = Arrays.stream(table.getDeclaredFields())
                .toArray(Field[]::new);

        for (Field field : declaredFields) {
            field.setAccessible(true);
            fillField(field, resultSet, entity);
        }
    }

    private void fillField(Field field, ResultSet resultSet, E entity) throws SQLException, IllegalAccessException {
        field.setAccessible(true);

        if (field.getType() == int.class || field.getType() == long.class) {
            field.set(entity, resultSet.getInt(field.getName()));
        } else if (field.getType() == LocalDate.class) {
            field.set(entity, LocalDate.parse(resultSet.getString(field.getName())));
        } else {
            field.set(entity, resultSet.getString(field.getName()));
        }
    }
}
