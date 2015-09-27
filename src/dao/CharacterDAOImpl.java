package dao;

import model.Character;
import org.hibernate.Session;
import utils.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

public class CharacterDAOImpl implements CharacterDAO {
    @Override
    public boolean addCharacter(model.Character character) {
        boolean isAdded = false;
        Session session = HibernateUtil.openSession();

        try {
            session.beginTransaction();
            session.save(character);
            HibernateUtil.commitTransaction(session);

            isAdded = true;

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return isAdded;
    }

    @Override
    public List<Character> CharacterList() {
        List<Character> characterList = new ArrayList<>();
        Session session = HibernateUtil.openSession();
        session.beginTransaction();
        characterList = session.createQuery("FROM Character").list();
        HibernateUtil.commitTransaction(session);
        return characterList;
    }

    @Override
    public void deleteCharacter(String CharacterName) {
        Session session = HibernateUtil.openSession();
        session.beginTransaction();
        Character character = (Character) session.load(Character.class, CharacterName);
        session.delete(character);
        HibernateUtil.commitTransaction(session);
    }

    @Override
    public void updateCharacter(Character character) {
        Session session = HibernateUtil.openSession();
        session.beginTransaction();
        session.update(character);
        HibernateUtil.commitTransaction(session);
    }
}
