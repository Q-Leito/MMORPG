package controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.*;
import service.ServerService;
import service.ServerServiceImpl;
import service.UserService;
import service.UserServiceImpl;
import utils.Constants;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.Set;

abstract class Controller {

    //region Fields

    private ServerService mServerService;
    private UserService mUserService;
    private User mUser;
    private ObservableList<User> mUsersList;
    private ObservableList<model.Character> mCharacterList;

    //endregion

    //region UI Controls

    @FXML
    private Label title;
    private Server mServer;

    //endregion


    //region Properties

    protected UserService getUserService() {
        return mUserService;
    }

    protected void setUserService(UserService mUserService) {
        this.mUserService = mUserService;
    }

    protected User getUser() {
        return mUser;
    }

    protected void setUser(User user) {
        mUser = user;
    }

    protected ObservableList<User> getUserList() {
        return mUsersList;
    }

    protected void setUserList(ObservableList<User> users) {
        mUsersList = users;
    }

    public ServerService getServerService() {
        return mServerService;
    }

    public void setServerService(ServerService mServerService) {
        this.mServerService = mServerService;
    }

    protected void setTitle(String header) {
        title.setText(header);
    }

    //endregion

    protected Controller() {
        setUserService(new UserServiceImpl());
        setServerService(new ServerServiceImpl());
    }

    //region Methods

    protected void load() {
    }

    protected <T> void showScene(Node node, String fxmlPath, String header, Object obj) {
        URL navigationUrl = getClass().getResource(fxmlPath);

        Parent root = null;

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(navigationUrl);

        try {
            loader.load();
            root = loader.getRoot();
        } catch (IOException e) {
            System.out.printf("\n--- %s ---\n", e.getMessage());
            e.printStackTrace();
        }

        if (root != null) {
            Scene rootScene = new Scene(root, Constants.APP_WIDTH, Constants.APP_HEIGHT);
            Stage primaryStage = (Stage) node.getScene().getWindow();

            String title = String.format("%s - %s", header, Constants.APP_NAME);

            primaryStage.setTitle(title);
            primaryStage.setScene(rootScene);
            primaryStage.setResizable(false);

            Controller controller = loader.getController();

            if (obj != null) {
                if (obj instanceof User) {
                    User user = (User) obj;
                    controller.setUser(user);
                } else if (obj instanceof ObservableList) {
                    ObservableList<User> userList = (ObservableList<User>) obj;
                    controller.setUserList(userList);
                } else if (obj instanceof Server) {
                    Server server = (Server) obj;
                    controller.setServer(server);
                }
            }

            controller.load();

            primaryStage.show();
        }
    }

    protected boolean validateText(String... values) {
        boolean isValidated = true;

        for (String v : values) {
            if (v == null || v.isEmpty()) {
                isValidated = false;
                break;
            }
        }

        return isValidated;
    }

    protected ImageView createImageBtnLayout(String imagePath, double width, double height) {
        Image addCharacterImg = new Image(getClass().getResourceAsStream(imagePath));
        ImageView imageView = new ImageView(addCharacterImg);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);

        return imageView;
    }

    protected String getRandomAvatarPath() {
        Random random = new Random();
        int avatarNr = 1 + random.nextInt(9);

        return String.format("%s/%s.jpg", Constants.IMAGE_AVATAR_PATH, avatarNr);
    }

    public void setServer(Server server) {
        mServer = server;
    }

    public Server getServer() {
        return mServer;
    }


    //endregion
}