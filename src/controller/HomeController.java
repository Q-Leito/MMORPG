package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import model.Server;
import service.ServerServiceImpl;
import utils.Constants;

import java.util.List;

public class HomeController extends Controller {

    //region UI control

    @FXML
    public VBox serverBox;

    //endregion

    private List<Server> servers;

    //region Methods

    @FXML
    public void initialize() {

        List<Server> servers = getServerService().ServerList();

        servers.forEach(this::createServers);
    }

    private void createServers(Server server) {

        Button serverBtn = createServerBtn(server.getServerName(), server.getServerAddress(), server.getServerConnectedUsers(), server.getServerMaxUsers());

        serverBtn.setOnAction(event -> {

            setServer(server);

            getServer().setUsers(getUser());
            getServer().setServerConnectedUsers(server.getServerConnectedUsers() + 1);

            boolean isAdded = getServerService().updateServer(getServer());

            if (isAdded) {
                serverBtn.setText(String.format("%s (%s) Users: %s/%s", server.getServerName(), server.getServerAddress(), server.getServerConnectedUsers(), server.getServerMaxUsers()));

                Node node = (Node) event.getSource();

                showScene(node, Constants.SERVER_FXML_PATH, server.getServerName(), getServer());
            }
        });

        serverBox.getChildren().add(0, serverBtn);
    }

    private Button createServerBtn(String serverName, String serverAddress, Integer serverConnectedUsers, Integer serverMaxUsers) {
        Button serverBtn = new Button();
        serverBtn.setMaxWidth(300.0d);
        serverBtn.setMaxHeight(50.0d);
        serverBtn.setMinWidth(300.0d);
        serverBtn.setMinHeight(50.0d);

        serverBtn.setText(String.format("%s (%s) Users: %s/%s", serverName, serverAddress, serverConnectedUsers, serverMaxUsers));

        serverBtn.setDisable(serverConnectedUsers >= serverMaxUsers);

        ImageView avatarImgBtnLayout = createImageBtnLayout(serverConnectedUsers >= serverMaxUsers ? "/images/full.png" : "/images/available.png", 15.0d, 15.0d);
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