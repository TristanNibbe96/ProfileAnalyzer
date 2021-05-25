package ProfileAnalyzer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

import java.io.File;
import java.util.HashSet;
import java.util.List;

public class MainWindowController {
    String directory = "C:\\Users\\Tristan_Nibbe\\Downloads";

    Parser parser = new Parser(directory);
    Analyzer analyzer = new Analyzer();

    String[] profileFiles;
    Boolean profilesLoaded = false;

    @FXML
    private Button AnalyzeButton;


    @FXML
    private MenuItem OpenOptionsButton;

    @FXML
    private VBox ProfileSelectorPane;

    @FXML
    private TextArea CommonWordsTextArea;

    @FXML
    private Button LoadProfilesButton;

    @FXML
    void OpenOptions(ActionEvent event) {

    }

    @FXML
    void LoadProfiles(ActionEvent event) {
        File directoryPath = new File(directory + "\\Reference_Profiles");
        profileFiles = directoryPath.list();

        for (String profile: profileFiles){
            ProfileSelectorPane.getChildren().add(new CheckBox(profile));
        }
        AnalyzeButton.disableProperty().setValue(false);
    }

    @FXML
    void AnalyzeProfile(ActionEvent event) {
        for(String file: profileFiles) {
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
