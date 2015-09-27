package dao;

import model.User;

import java.util.List;

public interface UserDAO {
    boolean addUser(User user);
    List<User> UserList();
    void deleteUser(String userName);
    void updateUser(User user);
}
