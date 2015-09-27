import controller.RegistrationController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {
    private RegistrationController mController = new RegistrationController();

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the font
        Font.loadFont(getClass().getResource("/fonts/Audiowide regular.ttf").toExternalForm(), 10);
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));

        primaryStage.setTitle("Login - MMORPG");
        primaryStage.setScene(new Scene(root, 960, 600));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
