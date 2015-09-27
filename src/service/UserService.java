package service;

import model.User;

import java.util.List;

public interface UserService {
    boolean addUser(User user);
    List<User> UserList();
    void deleteUser(String userName);
    void updateUser(User user);
}
