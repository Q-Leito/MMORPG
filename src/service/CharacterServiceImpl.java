package service;

import dao.CharacterDAO;
import dao.CharacterDAOImpl;
import model.Character;

import java.util.List;

public class CharacterServiceImpl implements CharacterService {
    private CharacterDAO mCharacterDAO = new CharacterDAOImpl();

    @Override
    public boolean addCharacter(model.Character character) {
        return mCharacterDAO.addCharacter(character);
    }

    @Override
    public List<Character> CharacterList() {
        return mCharacterDAO.CharacterList();
    }

    @Override
    public void deleteCharacter(String characterName) {
        mCharacterDAO.deleteCharacter(characterName);
    }

    @Override
    public void updateCharacter(Character character) {
        mCharacterDAO.updateCharacter(character);
    }
}
