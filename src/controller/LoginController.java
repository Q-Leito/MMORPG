package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.Server;
import model.User;
import utils.Constants;

import java.util.List;

public class LoginController extends Controller {

    //region  UI controls

    @FXML
    public Label messageLabel;

    @FXML
    public TextField usernameTextField;
    @FXML
    public PasswordField passwordTextField;
    @FXML
    public Button createUsersBtn;

    //endregion

    public LoginController() {
        createServers();
    }

    @FXML
    public void initialize() {
        long userListSize = getUserService().count();
        createUsersBtn.setVisible(!(userListSize > 1000));
    }

    public void createUsersBtn_Click(ActionEvent actionEvent) {
        for (int i = 0; i < 1001; i++) {
            String username = String.format("Peter%s", i);
            String password = String.format("welkom%s", i);
            String firstName = "Peter";
            String lastName = "de Vries";
            String iban = "123456";

            User user = new User(username, firstName, lastName, iban, password);
            boolean isAdded = getUserService().addUser(user);
            System.out.printf("User '%s' is added: %s \n", username, isAdded);
        }
    }

    //region Methods

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
        showScene(node, Constants.REGISTER_FXML_PATH, Constants.REGISTRATION_SCENE_HEADER, null);
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
        User user = getUserService().get(userNameInput, userPasswordInput);
        return user;
    }

    //endregion
}