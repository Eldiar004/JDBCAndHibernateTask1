package peaksoft.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import peaksoft.model.User;
import peaksoft.util.HibernateUtil;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    UserDaoJdbcImpl userDaoJdbc = new UserDaoJdbcImpl();

    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        String create = "create table if not exists users(" +
                "id serial primary key ," +
                "name varchar not null ," +
                "lastName varchar not null ," +
                "age smallint)";
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
        String delete = "drop table if exists users";
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            session.getTransaction().begin();
            session.createSQLQuery(delete).executeUpdate();
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
            session.beginTransaction();
            session.persist(new User(name , lastName , age));
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
            session.remove(user);
            session.getTransaction().commit();
        }
        catch (HibernateException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<User> getAllUsers() {
        String select = "select *  from users ";
        List<User> list = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            session.getTransaction().begin();
            list = session.createSQLQuery(select).getResultList();
            session.getTransaction().commit();

        }
        return list;
    }

    @Override
    public void cleanUsersTable() {
        String truncate = "truncate table users";
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            session.getTransaction().begin();
            session.createSQLQuery(truncate).executeUpdate();
            session.getTransaction().commit();

        }
        catch (HibernateException e){
            System.out.println(e.getMessage());
        }
    }
}
