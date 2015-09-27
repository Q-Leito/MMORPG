package dao;

import model.Server;
import org.hibernate.Session;
import utils.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

public class ServerDAOImpl implements ServerDAO {
    @Override
    public boolean addServer(Server server) {
        boolean isAdded = false;
        Session session = HibernateUtil.openSession();

        try {
            session.beginTransaction();
            session.save(server);
            HibernateUtil.commitTransaction(session);

            isAdded = true;

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return isAdded;
    }

    @Override
    public List<Server> ServerList() {
        List<Server> serverList = new ArrayList<>();
        Session session = HibernateUtil.openSession();
        session.beginTransaction();
        serverList = session.createQuery("FROM Server").list();
        HibernateUtil.commitTransaction(session);
        return serverList;
    }

    @Override
    public void deleteServer(String serverName) {
        Session session = HibernateUtil.openSession();
        session.beginTransaction();
        Server server = (Server) session.load(Character.class, serverName);
        session.delete(server);
        HibernateUtil.commitTransaction(session);
    }

    @Override
    public void updateServer(Server server) {
        Session session = HibernateUtil.openSession();
        session.beginTransaction();
        session.update(server);
        HibernateUtil.commitTransaction(session);
    }
}
