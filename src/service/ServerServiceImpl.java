package service;

import dao.MainDAO;
import model.Server;
import sun.applet.Main;
import utils.EntityEnum;

import java.util.List;

public class ServerServiceImpl extends MainDAO implements ServerService {

    @Override
    public boolean addServer(Server server) { return add(server); }

    @Override
    public List<Server> ServerList() { return getList(EntityEnum.Server); }

    @Override
    public void deleteServer(String serverName) { delete(serverName); }

    @Override
    public void updateServer(Server user) { update(user); }
}
