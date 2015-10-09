package service;

import dao.MainDAO;
import model.Character;
import utils.EntityEnum;

import java.util.List;

public class CharacterServiceImpl extends MainDAO implements CharacterService {

    @Override
    public boolean addCharacter(Character character) {
        return add(character);
    }

    @Override
    public List<Character> CharacterList() {
        return getList(EntityEnum.Character);
    }

    @Override
    public void deleteCharacter(String characterName) {
        delete(characterName);
    }

    @Override
    public void updateCharacter(Character character) {
        update(character);
    }

    @Override
    public String checkCharacterName(String characterName) {
        String query = String.format("SELECT mCharacterName FROM Character WHERE mCharacterName = '%s'", characterName);
        return get(query);
    }

    @Override
    public long count() {
        String query = "SELECT count(*) FROM Character";
        return get(query);
    }
}
