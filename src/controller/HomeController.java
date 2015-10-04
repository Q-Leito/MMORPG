package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import utils.Constants;

public class HomeController extends Controller {

    //region Methods

    public void initializeData() {
        String userFirstName = getUser().getFirstName();
        String userLastName = getUser().getLastName();

        String header = String.format("Welcome %s %s!", userFirstName, userLastName);
        setTitle(header);
    }

    public void handleLogoutBtn_Click(ActionEvent actionEvent) {
        Node node = (Node) actionEvent.getSource();
        showScene(node, Constants.LOGIN_FXML_PATH, Constants.LOGIN_SCENE_HEADER, null);
    }

    public void handleProfileBtn_Click(ActionEvent actionEvent) {
        Node node = (Node) actionEvent.getSource();
        showScene(node, Constants.PROFILE_FXML_PATH, Constants.PROFILE_SCENE_HEADER, getUser());
    }

    public void handleCharacterBtn_Click(ActionEvent actionEvent) {
        Node node = (Node) actionEvent.getSource();
        showScene(node, Constants.CHARACTER_FXML_PATH, Constants.CHARACTER_SCENE_HEADER, getUser());
    }

    //endregion
}