package service;

import model.Character;

import java.util.List;

public interface CharacterService {
    boolean addCharacter(Character character);
    List<Character> CharacterList();
    void deleteCharacter(String characterName);
    void updateCharacter(Character character);
    String checkCharacterName(String characterName);
    long count();
}
