package service;

import model.User;

import java.util.List;

public interface UserService {
    boolean addUser(User user);
    List<User> userList();
    boolean deleteUser(String userName);
    boolean updateUser(User user);
}
