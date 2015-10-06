package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.Server;
import model.User;
import utils.Constants;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class LoginController extends Controller implements Initializable {

    //region  UI controls

    @FXML
    public Label messageLabel;

    @FXML
    public TextField usernameTextField;
    @FXML
    public PasswordField passwordTextField;

    //endregion

    public LoginController() {

        List<User> users = getUserService().userList();

        if (users != null && !users.isEmpty()) {
            ObservableList<User> list = FXCollections.observableList(users);
            setUserList(list);
        } else {
            System.out.println("Database table doesn't have any user data");
        }

        createServers();
    }

    //region Methods

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        usernameTextField.setText("Quincy_Leito");
        passwordTextField.setText("password");
    }

    public void handleSignInBtn_Click(ActionEvent actionEvent) {

        String userNameInput = usernameTextField.getText();
        String userPasswordInput = passwordTextField.getText();

        boolean isSignedIn = signIn(userNameInput, userPasswordInput);

        if (isSignedIn) {
            Node node = (Node) actionEvent.getSource();
            showScene(node, Constants.HOME_FXML_PATH, Constants.HOME_SCENE_HEADER, getUser());
        }
    }

    public void handleRegisterBtn_Click(ActionEvent actionEvent) {
        Node node = (Node) actionEvent.getSource();
        showScene(node, Constants.REGISTER_FXML_PATH, Constants.REGISTRATION_SCENE_HEADER, getUserList());
    }

    private boolean signIn(String userName, String userPassword) {

        boolean isSignedIn = false;
        User user = findUser(userName, userPassword);

        if (user != null) {
            isSignedIn = true;
            setUser(user);
        }

        messageLabel.setText(user == null ? "Invalid username or password!" : Constants.EMPTY_STRING);

        return isSignedIn;
    }

    private void createServers() {

        List<Server> serverList = getServerService().ServerList();
        int serversAmount = serverList == null ? 0 : serverList.size();

        if (serversAmount <= 0) {

            Server server1 = new Server("199.00.22.1", "Server 1", "Europe", 100, 50);
            Server server2 = new Server("143.12.14.2", "Server 2", "Africa", 100, 100);
            Server server3 = new Server("321.64.12.1", "Server 3", "America", 100, 83);

            boolean isServerAdded;

            isServerAdded = getServerService().addServer(server1);
            System.out.printf("Server 1 created: %s\n", isServerAdded);
            isServerAdded = getServerService().addServer(server2);
            System.out.printf("Server 2 created: %s\n", isServerAdded);
            isServerAdded = getServerService().addServer(server3);
            System.out.printf("Server 3 created: %s\n", isServerAdded);
        }
    }

    private User findUser(String userNameInput, String userPasswordInput) {

        if (getUserList() == null) {
            return null;
        }

        for (User user : getUserList()) {

            String userName = user.getUsername();
            String userPassword = user.getPassword();

            boolean isUserNameMatched = userNameInput.equals(userName);
            boolean isUserPasswordMatched = userPasswordInput.equals(userPassword);

            if (isUserNameMatched && isUserPasswordMatched) {
                return user;
            }
        }

        return null;
    }

    //endregion
}