package model;

import org.hibernate.annotations.Proxy;
import utils.Constants;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Proxy(lazy = false)
public class User {

    @Id
//    @Index(name = "username_index", columnNames = { "user_name" })
    @Column(name = "user_name", nullable = false)
    private String mUsername;

    @Column(name = "balance")
    private Double mBalance;

    @Column(name = "first_name", nullable = false)
    private String mFirstName;

    @Column(name = "last_name", nullable = false)
    private String mLastName;

    @Column(name = "iban", nullable = false)
    private String mIban;

    @Column(name = "character_slots")
    private Integer mCharacterSlots;

    @Column(name = "last_payment")
    private Timestamp mLastPayment;

    @Column(name = "months_payed")
    private Integer mMonthsPayed;

    @Column(name = "password", nullable = false)
    private String mPassword;

    @Column(name = "banned")
    private Boolean mBanned;

    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinTable(name = "owns",
            joinColumns = {@JoinColumn(name = "user_name")},
            inverseJoinColumns = {@JoinColumn(name = "name")})
    @OrderBy("level")
    private Set<Character> mCharacters = new HashSet<>();

    @ManyToMany(mappedBy = "mUsers", fetch = FetchType.EAGER)
    private Set<Server> mServers = new HashSet<>();

    public User() {
        super();
    }

    public User(String username, String firstName, String lastName, String iban, String password) {
        super();
        mUsername = username;
        mBalance = Constants.USER_BALANCE;
        mCharacterSlots = Constants.USER_CHARACTER_SLOTS;
        mMonthsPayed = Constants.USER_MONTHS_PAYED;
        mBanned = Constants.USER_BANNED;
        mFirstName = firstName;
        mLastName = lastName;
        mIban = iban;
        mPassword = password;
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

    public void setCharacterSlots(Integer slots) {
        mCharacterSlots = slots;
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

    public void setCharacter(Character character) {

        if (character != null) {
            mCharacters.add(character);
        }
    }

    public Set<Server> getServer() {
        return mServers;
    }

    public void setServer(Server server) {
        mServers.add(server);
    }
}
