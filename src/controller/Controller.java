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
import model.User;
import service.UserService;
import service.UserServiceImpl;
import utils.Constants;

import java.io.IOException;
import java.net.URL;
import java.util.Random;

abstract class Controller {

    //region Fields

    private UserService mUserService;
    private User mUser;
    private ObservableList<User> mUsersList;

    //endregion

    //region UI Controls

    @FXML
    private Label title;

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

    protected void setTitle(String header) {
        title.setText(header);
    }

    //endregion

    protected Controller() {
        setUserService(new UserServiceImpl());
    }

    //region Methods

    protected void initializeData() {
    }

    protected void showScene(Node node, String fxmlPath, String header, Object obj) {
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
                }
            }

            controller.initializeData();

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

    //endregion
}