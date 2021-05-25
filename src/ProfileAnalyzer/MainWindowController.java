package ProfileAnalyzer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;

import java.io.File;
import java.util.HashSet;

public class MainWindowController {
    String directory = "C:\\Users\\Tristan_Nibbe\\Downloads";
    Parser parser = new Parser(directory);
    Analyzer analyzer = new Analyzer();

    @FXML
    private MenuItem AnalyzeButton;

    @FXML
    private TextArea CommonWordsTextArea;

    @FXML
    void AnalyzeProfile(ActionEvent event) {
        File directoryPath = new File(directory + "\\Reference_Profiles");
        String contents[] = directoryPath.list();

        for(String file: contents) {
            HashSet<String> parsedProfile = parser.parseProfile(file);

            analyzer.analyzeProfile(parser.parseProfile(file));
        }
        analyzer.printCommonWords(3);
    }

}
