package dao;

import model.User;
import org.hibernate.Session;
import org.hibernate.Query;
import utils.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    private final Session mSession;

    public UserDAOImpl() {
        mSession = HibernateUtil.getCurrentSession();
    }

    @Override
    public boolean addUser(User user) {

        boolean isAdded = false;

        try {
            mSession.beginTransaction();
            mSession.save(user);
            mSession.getTransaction().commit();

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
        int result;

        try {
            Query query = mSession.createQuery("FROM User");

            result = query.executeUpdate();

            if (result == 1)
            {
                userList = query.list();
            }

        } catch(Exception ex) {
            System.out.println(ex.getMessage());
        }

        return userList;
    }

    @Override
    public void deleteUser(String userName) {

        mSession.beginTransaction();
        User user = (User) mSession.load(User.class, userName);
        mSession.delete(user);
        mSession.getTransaction().commit();
    }

    @Override
    public void updateUser(User user) {

        mSession.beginTransaction();
        mSession.update(user);
        mSession.getTransaction().commit();
    }
}
