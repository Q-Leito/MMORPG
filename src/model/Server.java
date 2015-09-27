package model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "servers")
public class Server {

    @Id
    @Column(name = "address")
    private String mServerAddress;

    @Column(name = "name")
    private String mServerName;

    @Column(name = "location")
    private String mServerLocation;

    @Column(name = "max_users")
    private Integer mServerMaxUsers;

    @Column(name = "connected_users")
    private Integer mServerConnectedUsers;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "stores",
                joinColumns = {@JoinColumn(name = "address")},
                inverseJoinColumns = {@JoinColumn(name = "user_name")})
    private Set<User> mUsers = new HashSet<>();

    public Server() {
        super();
    }

    public Server(String serverAddress, String serverName, String serverLocation,
                  Integer serverMaxUsers, Integer serverConnectedUsers) {
        super();
        mServerAddress = serverAddress;
        mServerName = serverName;
        mServerLocation = serverLocation;
        mServerMaxUsers = serverMaxUsers;
        mServerConnectedUsers = serverConnectedUsers;
    }

    public String getServerAddress() {
        return mServerAddress;
    }

    public String getServerName() {
        return mServerName;
    }

    public String getServerLocation() {
        return mServerLocation;
    }

    public Integer getServerMaxUsers() {
        return mServerMaxUsers;
    }

    public Integer getServerConnectedUsers() {
        return mServerConnectedUsers;
    }

    public Set<User> getUsers() {
        return mUsers;
    }
}
