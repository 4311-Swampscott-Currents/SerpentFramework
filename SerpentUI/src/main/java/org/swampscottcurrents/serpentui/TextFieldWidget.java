package org.swampscottcurrents.serpentui;

import java.util.logging.Logger;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.shuffleboard.api.data.types.StringType;
import edu.wpi.first.shuffleboard.api.widget.Description;
import edu.wpi.first.shuffleboard.api.widget.ParametrizedController;
import edu.wpi.first.shuffleboard.api.widget.SimpleAnnotatedWidget;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;

@Description(dataTypes = { StringType.class }, name = "TextField")
@ParametrizedController(value = "TextFieldWidget.fxml")
public class TextFieldWidget extends SimpleAnnotatedWidget<String> {

    @FXML
    private Pane viewingPane;
    @FXML
    private TextArea viewedArea;

    @Override
    public Pane getView() {
        return viewingPane;
    }

    @FXML
    private void initialize() {
        viewedArea.textProperty().bind(dataOrDefault);
        viewedArea.prefWidthProperty().bind(viewingPane.prefWidthProperty());
        viewedArea.prefHeightProperty().bind(viewingPane.prefHeightProperty());
    }
}