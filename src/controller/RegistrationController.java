package controller;

import com.google.common.base.Stopwatch;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import model.User;
import utils.Constants;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

public class RegistrationController extends Controller implements Initializable {

    //region UI Controls
    @FXML
    public Label messageLabel;

    @FXML
    public Button backBtn;

    @FXML
    public TextField userNameField;
    @FXML
    public TextField firstNameField;
    @FXML
    public TextField lastNameField;
    @FXML
    public TextField ibanField;
    @FXML
    public PasswordField passwordField;

    @FXML
    public Button registerButton;
    //endregion

    boolean userNamesExists;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ImageView backImgBtnLayout = createImageBtnLayout(Constants.BACK_IMAGE_PATH, Constants.BACK_IMAGE_WIDTH, Constants.BACK_IMAGE_HEIGHT);
        backBtn.setGraphic(backImgBtnLayout);
    }

    public void handleRegisterBtn_Click(ActionEvent actionEvent) {

        String userName = userNameField.getText();
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String password = passwordField.getText();
        String iban = ibanField.getText();

        Node node = (Node) actionEvent.getSource();

        boolean isValidated = validateText(userName, firstName, lastName, password, iban);

        if (isValidated) {
            registerUser(node, userName, firstName, lastName, password, iban);
        }

        messageLabel.setText(!isValidated ? "All fields are required!" : userNamesExists ? String.format("Sorry, but %s already exist. Try another!", userName) : Constants.EMPTY_STRING);
    }

    public void handleBackBtn_Click(ActionEvent actionEvent) {
        Node node = (Node) actionEvent.getSource();
        showScene(node, Constants.LOGIN_FXML_PATH, Constants.LOGIN_SCENE_HEADER, null);
    }

    private void registerUser(Node node, String userName, String firstName, String lastName, String password, String iban) {

        userNamesExists = findUser(userName);

        if (!userNamesExists) {
            User newUser = new User(userName, firstName, lastName, iban, password);
            setUser(newUser);

            boolean isAdded = addUser(newUser);
            if (isAdded) {
                showScene(node, Constants.HOME_FXML_PATH, Constants.HOME_SCENE_HEADER, getUser());
            }
        }
    }

    private boolean addUser(User user) {
        return getUserService().addUser(user);
    }

    private boolean findUser(String userNameInput) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        String name = getUserService().checkUsername(userNameInput);
        stopwatch.stop();

        stopwatch.elapsed(TimeUnit.MILLISECONDS);
        System.out.println("Execute time: " + stopwatch);

        return name != null && !name.isEmpty();
    }
}