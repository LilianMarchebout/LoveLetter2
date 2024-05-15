package loveletter.src;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    public static Scene scene;
    public static Stage stage;
    @Override
    public void start(Stage stage) throws IOException {
        App.scene = new Scene(loadFXML("/game/src/view/principal"), 800, 600);
        App.stage = stage;
        App.stage.setScene(App.scene);
        App.stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML("/game/src/view/" + fxml));
    }

    public static void setWindowDimensions(double width, double height) {
        App.stage.setWidth(width);
        App.stage.setHeight(height);
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
