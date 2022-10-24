package peaksoft.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.beanvalidation.HibernateTraversableResolver;
import peaksoft.model.User;
import peaksoft.util.HibernateUtil;
import peaksoft.util.Util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    UserDaoJdbcImpl userDaoJdbc = new UserDaoJdbcImpl();

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        String create = "create table users(" +
                "id serial primary key ," +
                "name varchar not null ," +
                "lastName varchar not null ," +
                "age int)";
        try{
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.getTransaction().begin();
            session.createSQLQuery(create);
            session.getTransaction().commit();
            session.close();
            System.out.println("Table created successfully!");
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        }

    }


    @Override
    public void dropUsersTable() {
        String delete = "drop table users;";
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            session.getTransaction().begin();
            session.createSQLQuery(delete);
            session.getTransaction().commit();
            session.close();
        }
        catch (HibernateException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.getTransaction().begin();
            User user = new User(name , lastName , age);
            session.persist(user);
            session.getTransaction().commit();
            session.close();
        }
        catch (HibernateException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            session.getTransaction().begin();
            User user = session.find(User.class , id);
            session.delete(user);
            session.getTransaction().commit();
            session.close();
        }
        catch (HibernateException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<User> getAllUsers() {
        String select = "select * from users";
        List<User> list;
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            session.getTransaction().begin();
            list = session.createQuery(select).list();
            session.getTransaction().commit();
            session.close();

        }
        return list;
    }

    @Override
    public void cleanUsersTable() {
        String truncate = "truncate table users";
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            session.getTransaction().begin();
            session.createSQLQuery(truncate);
            session.getTransaction().commit();
            session.close();
        }
        catch (HibernateException e){
            System.out.println(e.getMessage());
        }
    }
}
