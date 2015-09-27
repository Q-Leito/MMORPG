package service;

import dao.ServerDAO;
import dao.ServerDAOImpl;
import model.Server;

import java.util.List;

public class ServerServiceImpl implements ServerService {
    private ServerDAO mServerDAO = new ServerDAOImpl();

    @Override
    public boolean addServer(Server server) {
        return mServerDAO.addServer(server);
    }

    @Override
    public List<Server> ServerList() {
        return mServerDAO.ServerList();
    }

    @Override
    public void deleteServer(String serverName) {
        mServerDAO.deleteServer(serverName);
    }

    @Override
    public void updateServer(Server user) {
        mServerDAO.updateServer(user);
    }
}
