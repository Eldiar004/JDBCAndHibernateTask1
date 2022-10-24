package peaksoft.dao;

import peaksoft.model.User;
import peaksoft.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJdbcImpl implements UserDao {

    public UserDaoJdbcImpl() {

    }

    public void createUsersTable() {
        Connection connection = Util.connection();
        String query = "create table users(" +
                "id serial primary key ," +
                "name varchar ," +
                "lastName varchar ," +
                "age INTEGER)";
        try (Connection connection1 = Util.connection()){
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();
        }
        catch ( SQLException e ) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        String delete = "drop table users;";
        Connection connection = Util.connection();
        try (Connection connection1 = Util.connection()){
            PreparedStatement preparedStatement = connection1.prepareStatement(delete);
            preparedStatement.execute();
        }
        catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String insert = "insert into users(name, lastname, age) " +
                "VALUES (? , ? , ?) ";
        try (Connection connection1 = Util.connection()){
            PreparedStatement preparedStatement = connection1.prepareStatement(insert);
            preparedStatement.setString(1 , name);
            preparedStatement.setString(2 , lastName);
            preparedStatement.setInt(3 , age);
            preparedStatement.executeUpdate();
        }
        catch (SQLException e ) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        String delete = "delete from users where id = ?";
        try (Connection connection = Util.connection()){
            PreparedStatement preparedStatement = connection.prepareStatement(delete);
            preparedStatement.setLong(1 , id);
            preparedStatement.executeUpdate();
        }
        catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        Connection connection = null;
        String select = "SELECT * FROM users";
        List<User> list = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(select)) {
            while (resultSet.next()) {
                list.add(new User(resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("lastName"),
                        resultSet.getByte("age")));
            }
            return list;
        } catch (SQLException e) {
            System.out.println("Table not found!!!");
            throw new RuntimeException(e);

        }
    }

    public void cleanUsersTable() {
        String truncate = "truncate table users";
        try (Connection connection = Util.connection()){
            PreparedStatement preparedStatement = connection.prepareStatement(truncate);
            preparedStatement.execute();
        }
        catch (SQLException e){
            System.out.println("Table not found!");
        }
    }
}
