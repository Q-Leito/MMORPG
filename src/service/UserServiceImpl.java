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
    public void deleteUser(String userName) {
        delete(userName);
    }

    @Override
    public void updateUser(User user) {
        update(user);
    }
}
