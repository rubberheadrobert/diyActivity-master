package sample.Controllers;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

public class OptionsController {
    @FXML
    private Label optionsLabel;
    @FXML
    private Label sliderDescriptionLabel;
    @FXML
    private Label secondsLabel;

    @FXML
    private Button backButton;

    @FXML
    private Slider secondsSlider;

    @FXML
    private StackPane stackPane;

    private static int seconds;
    private ObjectProperty<Font> bigFontTracking = new SimpleObjectProperty<Font>(Font.getDefault());
    private ObjectProperty<Font> smallFontTracking = new SimpleObjectProperty<Font>(Font.getDefault());

    public void initialize(){
        secondsSlider.setValue(30.0);
        setSecondsLabel();

        backButton.prefHeightProperty().bind(stackPane.heightProperty());
        backButton.prefWidthProperty().bind(stackPane.widthProperty());

        backButton.fontProperty().bind(smallFontTracking);
        secondsLabel.fontProperty().bind(smallFontTracking);
        sliderDescriptionLabel.fontProperty().bind(smallFontTracking);
        optionsLabel.fontProperty().bind(bigFontTracking);

        stackPane.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldHeight, Number newHeight) {
                bigFontTracking.set(Font.font(newHeight.doubleValue()/22));
                smallFontTracking.set(Font.font(newHeight.doubleValue()/40));
            }
        });

    }

    public void openHomeScene() {
        try {
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.close();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("..\\Views\\home.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            stage = new Stage();
            stage.setTitle("DIY ACTIVITY");
            stage.setScene(new Scene(root,300,500));
            stage.show();
        } catch (IOException e) {
            System.out.println("Couldn't open next window");
        }
    }

    public void setSecondsLabel(){
        seconds = (int)secondsSlider.getValue();
        secondsLabel.setText(Integer.toString(seconds));
        System.out.println(secondsLabel.getText());
    }

    public static int getSecondsSliderValue(){
        return seconds;
    }

}
