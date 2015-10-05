package controller;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import model.Server;
import model.User;
import utils.Constants;

public class ServerController extends Controller {

    public void disconnectFromServer(ActionEvent actionEvent) {

        getServer().setServerConnectedUsers(getServer().getServerConnectedUsers() - 1);

        User user = getServer().getUsers().iterator().next();

        getServer().getUsers().remove(user);
        boolean isUpdated = getServerService().updateServer(getServer());

        System.out.printf("Server is updated: %s", isUpdated);

        Node node = (Node) actionEvent.getSource();
        showScene(node, Constants.HOME_FXML_PATH, Constants.HOME_SCENE_HEADER, user);
    }
}
