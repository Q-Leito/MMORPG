package dao;

import org.hibernate.Session;
import utils.EntityEnum;
import utils.HibernateUtil;

import java.util.List;

public abstract class MainDAO
{
    private HibernateUtil hibernate = HibernateUtil.getInstance();

    protected boolean add(Object obj) {
        boolean isAdded = false;

        try {
            Session session = hibernate.openSession();
            session.beginTransaction();
            session.save(obj);
            hibernate.commitTransaction(session);

            isAdded = true;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return isAdded;
    }

    protected boolean update(Object obj) {

        boolean isUpdated = false;

        try
        {
            Session session = hibernate.openSession();
            session.beginTransaction();
            session.saveOrUpdate(obj);
            hibernate.commitTransaction(session);

            isUpdated = true;
        } catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }

        return isUpdated;
    }

    protected boolean delete(Object obj) {

        boolean isDeleted = false;

        if (obj == null) return isDeleted;

        try
        {
            Session session = hibernate.openSession();
            session.beginTransaction();
            session.update(obj);
            hibernate.commitTransaction(session);

            isDeleted = true;
        } catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }

        return isDeleted;
    }

    protected <T> List<T> getList(EntityEnum entity)
    {
        return get(entity);
    }

    private <T> List<T> get(EntityEnum entity) {
        Object result = null;

        try
        {
            Session session = hibernate.openSession();
            session.beginTransaction();
            result = session.createQuery(String.format("FROM %s", entity)).list();
            hibernate.commitTransaction(session);
        } catch (Exception ex)
        {
            ex.printStackTrace();

            System.out.println(ex.getMessage());
        }

        return (List<T>) result;
    }
}