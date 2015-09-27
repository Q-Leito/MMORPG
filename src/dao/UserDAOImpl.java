package dao;

import model.User;
import org.hibernate.Session;
import utils.HibernateUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    @Override
    public boolean addUser(User user) {

        boolean isAdded = false;

        Session session = HibernateUtil.openSession();

        try {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();

            isAdded = true;

        } catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        } finally {
            session.close();
        }

        return isAdded;
    }

    @Override
    public List<User> UserList() {
        List<User> userList = new ArrayList<>();
        Session session = HibernateUtil.openSession();
        session.beginTransaction();
        userList = session.createQuery("FROM User").list();
        session.getTransaction().commit();
        session.close();
        return userList;
    }

    @Override
    public void deleteUser(String userName) {
        Session session = HibernateUtil.openSession();
        session.beginTransaction();
        User user = (User) session.load(User.class, userName);
        session.delete(user);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void updateUser(User user) {
        Session session = HibernateUtil.openSession();
        session.beginTransaction();
        session.update(user);
        session.getTransaction().commit();
        session.close();
    }
}
