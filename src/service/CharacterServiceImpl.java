package service;

import dao.MainDAO;
import model.Character;
import utils.EntityEnum;

import java.util.List;

public class CharacterServiceImpl extends MainDAO implements CharacterService {

    @Override
    public boolean addCharacter(model.Character character) { return add(character); }

    @Override
    public List<Character> CharacterList() { return getList(EntityEnum.Character); }

    @Override
    public void deleteCharacter(String characterName) {
        delete(characterName);
    }

    @Override
    public void updateCharacter(Character character) {
        update(character);
    }
}
