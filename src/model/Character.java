package model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "characters")
public class Character {

    @Id
    @Column(name = "name")
    private String mCharacterName;

    @Column(name = "class")
    private Integer mCharacterClass;

    @Column(name = "race")
    private String mCharacterRace;

    @Column(name = "level")
    private Integer mCharacterLevel;

    @ManyToMany(mappedBy="mCharacters")
    private Set<User> mUsers = new HashSet<>();

    public Character() {
        super();
    }

    public Character(String characterName, Integer characterClass, String characterRace,
                     Integer characterLevel) {
        super();
        mCharacterName = characterName;
        mCharacterClass = characterClass;
        mCharacterRace = characterRace;
        mCharacterLevel = characterLevel;
    }

    public String getCharacterName() {
        return mCharacterName;
    }

    public Integer getCharacterClass() {
        return mCharacterClass;
    }

    public String getCharacterRace() {
        return mCharacterRace;
    }

    public Integer getCharacterLevel() {
        return mCharacterLevel;
    }

    public Set<User> getUsers() {
        return mUsers;
    }
}
