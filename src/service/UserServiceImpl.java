package service;

import dao.MainDAO;
import model.User;
import utils.EntityEnum;

import java.util.List;

public class UserServiceImpl extends MainDAO implements UserService {

    @Override
    public boolean addUser(User user) {
        return add(user);
    }

    @Override
    public List<User> userList() { return getList(EntityEnum.User); }

    @Override
    public boolean deleteUser(String userName) {
        return delete(userName);
    }

    @Override
    public boolean updateUser(User user) {
        return update(user);
    }

    @Override
    public String checkUsername(String userName) {

        String query = String.format("SELECT mUsername FROM User WHERE mUsername = '%s'",userName);

        return get(query);
    }
}