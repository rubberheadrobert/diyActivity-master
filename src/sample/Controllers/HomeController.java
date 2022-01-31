package sample.Controllers;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.io.IOException;

public class HomeController {

    @FXML
    private Button playButton;
    @FXML
    private Button optionsButton;
    @FXML
    private Button rulesButton;
    @FXML
    private StackPane stackPane;

    private ObjectProperty<Font> fontTracking = new SimpleObjectProperty<Font>(Font.getDefault());


    public void initialize(){
        stackPane.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldHeight, Number newHeight) {
                fontTracking.set(Font.font(newHeight.doubleValue()/10));
            }
        });

        playButton.prefHeightProperty().bind(stackPane.heightProperty());
        playButton.prefWidthProperty().bind(stackPane.widthProperty());
        optionsButton.prefHeightProperty().bind(stackPane.heightProperty());
        optionsButton.prefWidthProperty().bind(stackPane.widthProperty());
        rulesButton.prefHeightProperty().bind(stackPane.heightProperty());
        rulesButton.prefWidthProperty().bind(stackPane.widthProperty());
    }

    public void anyButtonClickedAction(ActionEvent event){
        if(event.getSource() instanceof Button){
            Button buttonClicked = (Button) event.getSource();
            String nextFxmlName;
            String nextSceneTitle;
            if(buttonClicked.equals(playButton)){
                nextFxmlName = "..\\Views\\enterWords.fxml";
                nextSceneTitle = "Enter Words";
            } else if(buttonClicked.equals(optionsButton)){
                nextFxmlName = "..\\Views\\options.fxml";
                nextSceneTitle = "..\\Views\\Options";
            } else{
                nextFxmlName = "..\\Views\\rules.fxml";
                nextSceneTitle = "Rules";
            }
            openScene(buttonClicked,nextFxmlName,nextSceneTitle);
        }
    }

    private void openScene(Button buttonClicked, String nextFxmlName, String nextSceneTitle){
        try {
            Stage stage = (Stage) buttonClicked.getScene().getWindow();
            stage.close();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(nextFxmlName));
            Parent root = (Parent) fxmlLoader.load();
            stage = new Stage();
            stage.setTitle(nextSceneTitle);
            stage.setScene(new Scene(root,300,500));
            stage.show();
        } catch(IOException e) {
            System.out.println("Couldn't open next window");
            e.printStackTrace();
        }
    }
}




