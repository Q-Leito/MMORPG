package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import model.Server;
import utils.Constants;

import java.util.List;

public class HomeController extends Controller {

    //region UI control

    @FXML
    public Label messageLabel;

    @FXML
    public VBox serverBox;

    //endregion

    //region Methods

    @FXML
    public void initialize() {

        List<Server> servers = getServerService().ServerList();
        servers.forEach(this::createServers);
    }

    private void createServers(Server server) {

        String serverName = server.getServerName();
        String serverAddress = server.getServerAddress();
        int serverConnectedUsers = server.getServerConnectedUsers();
        int serverMaxUsers = server.getServerMaxUsers();
        boolean isServerAvailable = serverConnectedUsers < serverMaxUsers;
        String imagePath = isServerAvailable ? Constants.SERVER_AVAILABLE_IMAGE_PATH : Constants.SERVER_FULL_IMAGE_PATH;

        Button serverButton = createServerButton(serverName, serverAddress, serverConnectedUsers, serverMaxUsers, imagePath);
        serverButton.setDisable(!isServerAvailable);

        serverButton.setOnAction(actionEvent -> {

            boolean isAdded = connectToServer(server);

            if (isAdded) {
                Node node = (Node) actionEvent.getSource();
                showScene(node, Constants.SERVER_FXML_PATH, serverName, getServer());
            }
        });

        serverBox.getChildren().add(0, serverButton);
    }

    private boolean connectToServer(Server server) {

        int charactersSize = getUser().getCharacters().size();

        if (charactersSize > 0) {
            setServer(server);

            getServer().setUsers(getUser());
            int connectedUsers = getServer().getServerConnectedUsers() + 1;
            getServer().setServerConnectedUsers(connectedUsers);

            return getServerService().updateServer(getServer());
        }

        messageLabel.setText("No characters are created");

        return false;
    }

    private Button createServerButton(String serverName, String serverAddress, Integer serverConnectedUsers, Integer serverMaxUsers, String imagePath) {
        Button serverBtn = new Button();

        serverBtn.setPrefSize(Constants.SERVER_BUTTON_WIDTH, Constants.SERVER_BUTTON_HEIGHT);
        serverBtn.setText(String.format("%s (%s) Users: %s/%s", serverName, serverAddress, serverConnectedUsers, serverMaxUsers));

        ImageView avatarImgBtnLayout = createImageBtnLayout(imagePath, Constants.SERVER_IMAGE_WIDTH, Constants.SERVER_IMAGE_HEIGHT);
        serverBtn.setGraphic(avatarImgBtnLayout);

        return serverBtn;
    }

    public void load() {
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