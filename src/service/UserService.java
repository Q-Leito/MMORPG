package service;

import model.User;

import java.util.List;

public interface UserService {
    boolean addUser(User user);
    List<User> userList();
    List<Character> characterList(String query);
    boolean deleteUser(String userName);
    boolean updateUser(User user);
}
