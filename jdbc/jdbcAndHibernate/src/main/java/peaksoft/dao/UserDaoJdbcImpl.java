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
        String query = "" +
                "create table if not exists users(" +
                "id serial primary key ," +
                "name varchar ," +
                "lastName varchar ," +
                "age smallint);";
        try (Connection connection1 = Util.connection()){
            Statement statement = connection1.createStatement();
            statement.executeUpdate(query);
            System.out.println("Table created successfully!");
        }
        catch ( SQLException e ) {
            System.out.println(e.getMessage());
        }
    }

    public void dropUsersTable() {
        String delete = "drop table if exists users;";
        try (Connection connection1 = Util.connection()){
            PreparedStatement preparedStatement = connection1.prepareStatement(delete);
            preparedStatement.execute();
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String insert = "insert into users(name, lastname, age) " +
                "VALUES (? , ? , ?); ";
        try (Connection connection1 = Util.connection()){
            PreparedStatement preparedStatement = connection1.prepareStatement(insert);
            preparedStatement.setString(1 , name);
            preparedStatement.setString(2 , lastName);
            preparedStatement.setInt(3 , age);
            preparedStatement.execute();
        }
        catch (SQLException e ) {
            System.out.println(e.getMessage());
        }
    }

    public void removeUserById(long id) {
        String delete = "delete from users where id = ?;";
        try (Connection connection = Util.connection()){
            PreparedStatement preparedStatement = connection.prepareStatement(delete);
            preparedStatement.setLong(1 , id);
            preparedStatement.executeUpdate();
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<User> getAllUsers() throws SQLException {
        String select = "SELECT * FROM users;";

        try (Connection statement = Util.connection();
             Statement statement1 = statement.createStatement()) {
            ResultSet resultSet = statement1.executeQuery(select);
            List<User> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(new User(
                        resultSet.getString("name"),
                        resultSet.getString("lastname"),
                        resultSet.getByte("age")));
            }
            return list;
        } catch (SQLException e) {
            System.out.println("Table not found!!!");
            throw new SQLException(e);
        }

    }

    public void cleanUsersTable() {
        String truncate = "truncate table users;";
        try (Connection connection = Util.connection()){
            Statement preparedStatement = connection.createStatement();
            preparedStatement.executeUpdate(truncate);
        }
        catch (SQLException e){
            System.out.println("Table not found!");
        }
    }
}
