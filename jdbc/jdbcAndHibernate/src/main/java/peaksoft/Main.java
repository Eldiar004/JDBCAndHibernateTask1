package peaksoft;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import peaksoft.dao.UserDaoHibernateImpl;
import peaksoft.dao.UserDaoJdbcImpl;
import peaksoft.model.User;
import peaksoft.service.UserServiceImpl;
import peaksoft.util.HibernateUtil;

import javax.swing.text.html.parser.Entity;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        UserDaoHibernateImpl userDaoHibernate = new UserDaoHibernateImpl();
//        userDaoHibernate.saveUser("Eldiar" , "Rasulov" , (byte) 18);
//        userDaoHibernate.saveUser("Boki" , "Bolotov" , (byte) 20);
//        userDaoHibernate.saveUser("Nursultan" , "Erkinbekov" , (byte) 23);
        userDaoHibernate.cleanUsersTable();

    }
}

