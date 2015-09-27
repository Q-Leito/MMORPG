package dao;

import model.User;
import org.hibernate.Session;
import utils.HibernateUtil;

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
            HibernateUtil.commitTransaction(session);

            isAdded = true;

        } catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }

        return isAdded;
    }

    @Override
    public List<User> UserList() {
        List<User> userList = new ArrayList<>();
        Session session = HibernateUtil.openSession();
        session.beginTransaction();
        userList = session.createQuery("FROM User").list();
        HibernateUtil.commitTransaction(session);
        return userList;
    }

    @Override
    public void deleteUser(String userName) {
        Session session = HibernateUtil.openSession();
        session.beginTransaction();
        User user = (User) session.load(User.class, userName);
        session.delete(user);
        HibernateUtil.commitTransaction(session);
    }

    @Override
    public void updateUser(User user) {
        Session session = HibernateUtil.openSession();
        session.beginTransaction();
        session.update(user);
        HibernateUtil.commitTransaction(session);
    }
}
