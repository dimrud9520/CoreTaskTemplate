package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl extends Util implements UserDao {
    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.
                     prepareStatement("CREATE TABLE USER (id INT AUTO_INCREMENT NOT NULL,name VARCHAR(20),lastname VARCHAR(20),age INT,PRIMARY KEY(id));")) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            return;
        }
    }

    public void dropUsersTable() {
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("DROP TABLE User;")) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            return;
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO USER (ID,NAME, LASTNAME, AGE) VALUES (id,?, ?, ?);", new String[]{"id", "name", "lastname", "age"})) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                int id = (int) rs.getLong(1);
            }
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM USER WHERE ID=?;")) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery("SELECT ID, NAME, LASTNAME, AGE FROM USER;");
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("ID"));
                user.setName(rs.getString("NAME"));
                user.setLastName(rs.getString("LASTNAME"));
                user.setAge(rs.getByte("AGE"));
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    public void cleanUsersTable() {
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("TRUNCATE TABLE USER;")) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
