package ProfileAnalyzer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;

import java.io.File;
import java.util.HashSet;
import java.util.List;

public class MainWindowController {
    String directory = "C:\\Users\\Tristan_Nibbe\\Downloads";
    Parser parser = new Parser(directory);
    Analyzer analyzer = new Analyzer();

    @FXML
    private MenuItem AnalyzeButton;

    @FXML
    private MenuItem OpenOptionsButton;

    @FXML
    private TextArea CommonWordsTextArea;

    @FXML
    void OpenOptions(ActionEvent event) {

    }

    @FXML
    void AnalyzeProfile(ActionEvent event) {
        File directoryPath = new File(directory + "\\Reference_Profiles");
        String contents[] = directoryPath.list();

        for(String file: contents) {
            HashSet<String> parsedProfile = parser.parseProfile(file);

            analyzer.analyzeProfile(parser.parseProfile(file));
        }
        printCommonWords(analyzer.getCommonWords(3));
    }

    void printCommonWords(List<String> commonWords){
        for(String s: commonWords){
            CommonWordsTextArea.appendText(s + "\n");
        }
    }

}
