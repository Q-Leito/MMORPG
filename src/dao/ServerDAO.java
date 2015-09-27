package dao;

import model.Server;

import java.util.List;

public interface ServerDAO {
    boolean addServer(Server server);
    List<Server> ServerList();
    void deleteServer(String serverName);
    void updateServer(Server server);
}
