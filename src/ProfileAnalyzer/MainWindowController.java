package ProfileAnalyzer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;

public class MainWindowController {

    @FXML
    private MenuItem AnalyzeButton;

    @FXML
    private TextArea CommonWordsTextArea;

    @FXML
    void AnalyzeProfile(ActionEvent event) {
        CommonWordsTextArea.appendText("adfsd");
        System.out.println("ran");
    }

}
