package model;

import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Proxy(lazy=false)
public class User {

    @Id
    @Column(name = "user_name")
    private String mUsername;

    @Column(name = "balance")
    private Double mBalance;

    @Column(name = "first_name")
    private String mFirstName;

    @Column(name = "last_name")
    private String mLastName;

    @Column(name = "iban")
    private String mIban;

    @Column(name = "character_slots")
    private Integer mCharacterSlots;

    @Column(name = "last_payment")
    private Timestamp mLastPayment;

    @Column(name = "months_payed")
    private Integer mMonthsPayed;

    @Column(name = "password")
    private String mPassword;

    @Column(name = "banned")
    private Boolean mBanned;

    @ManyToMany(cascade = {CascadeType.ALL}, fetch=FetchType.EAGER)
    @JoinTable(name = "owns",
            joinColumns = {@JoinColumn(name = "user_name")},
            inverseJoinColumns = {@JoinColumn(name = "name")})
    private Set<Character> mCharacters = new HashSet<>();

    @ManyToMany(mappedBy = "mUsers")
    private Set<Server> mServers = new HashSet<>();

    public User() {
        super();
    }

    public User(String username, Double balance, String firstName, String lastName,
                String iban, Integer characterSlots, Timestamp lastPayment,
                Integer monthsPayed, String password, Boolean banned) {
        super();
        mUsername = username;
        mBalance = balance;
        mFirstName = firstName;
        mLastName = lastName;
        mIban = iban;
        mCharacterSlots = characterSlots;
        mLastPayment = lastPayment;
        mMonthsPayed = monthsPayed;
        mPassword = password;
        mBanned = banned;
    }

    public String getUsername() {
        return mUsername;
    }

    public Double getBalance() {
        return mBalance;
    }

    public void setBalance(Double balance) {
        mBalance = balance;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public String getIban() {
        return mIban;
    }

    public Integer getCharacterSlots() {
        return mCharacterSlots;
    }

    public Timestamp getLastPayment() {
        return mLastPayment;
    }

    public void setLastPayment(Timestamp lastPayment) {
        mLastPayment = lastPayment;
    }

    public Integer getMonthsPayed() {
        return mMonthsPayed;
    }

    public void setMonthsPayed(Integer monthsPayed) {
        mMonthsPayed = monthsPayed;
    }

    public String getPassword() {
        return mPassword;
    }

    public Boolean getBanned() {
        return mBanned;
    }

    public Set<Character> getCharacters() {
        return mCharacters;
    }

    public Set<Server> getServers() {
        return mServers;
    }

    public void setCharacter(Character character) {

        if (character != null) {
            mCharacters.add(character);
        }
    }

    public void setCharacterSlots(Integer slots) {
        mCharacterSlots = slots;
    }
}
