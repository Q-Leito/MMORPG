package dao;

import model.Character;

import java.util.List;

public interface CharacterDAO {
    boolean addCharacter(Character character);
    List<Character> CharacterList();
    void deleteCharacter(String CharacterName);
    void updateCharacter(Character character);
}
