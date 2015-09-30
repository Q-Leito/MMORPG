package dao;

import org.hibernate.Session;
import utils.EntityEnum;
import utils.HibernateUtil;

import java.util.List;

public abstract class MainDAO
{
    protected boolean add(Object obj) {
        boolean isAdded = false;

        try {
            Session session = HibernateUtil.openSession();
            session.beginTransaction();
            session.save(obj);
            HibernateUtil.commitTransaction(session);

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
            Session session = HibernateUtil.openSession();
            session.beginTransaction();
            session.saveOrUpdate(obj);
            HibernateUtil.commitTransaction(session);

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
            Session session = HibernateUtil.openSession();
            session.beginTransaction();
            session.update(obj);
            HibernateUtil.commitTransaction(session);

            isDeleted = true;
        } catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }

        return isDeleted;
    }

    protected <T> T get(Class<T> entity, String columnValue) {

        Object obj = null;

        try {
            Session session = HibernateUtil.openSession();
            session.beginTransaction();
            obj = session.load(entity, columnValue);

            session.update(obj);

            HibernateUtil.commitTransaction(session);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return (T) obj;
    }

    protected <T> List<T> getList(EntityEnum entity)
    {
        return get(entity);
    }

    private <T> List<T> get(EntityEnum entity) {
        Object result = null;

        try
        {
            Session session = HibernateUtil.openSession();
            session.beginTransaction();
            result = session.createQuery(String.format("FROM %s", entity)).list();
            HibernateUtil.commitTransaction(session);
        } catch (Exception ex)
        {
            ex.printStackTrace();

            System.out.println(ex.getMessage());
        }

        return (List<T>) result;
    }
}