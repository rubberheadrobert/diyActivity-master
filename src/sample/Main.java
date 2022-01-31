package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Views/home.fxml"));
        primaryStage.setTitle("DIY ACTIVITY");
        primaryStage.setScene(new Scene(root, 270, 510));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
