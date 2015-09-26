package service;

import dao.UserDAO;
import dao.UserDAOImpl;
import model.User;

import java.util.List;

public class UserServiceImpl  implements UserService {
    private UserDAO mUserDAO = new UserDAOImpl();

    @Override
    public void addUser(User user) {
        mUserDAO.addUser(user);
    }

    @Override
    public List<User> UserList() {
        return mUserDAO.UserList();
    }

    @Override
    public void deleteUser(String userName) {
        mUserDAO.deleteUser(userName);
    }

    @Override
    public void updateUser(User user) {
        mUserDAO.updateUser(user);
    }
}
