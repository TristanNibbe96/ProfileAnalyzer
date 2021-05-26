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
    CheckBox[] profileCheckboxes;

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
        ProfileSelectorPane.getChildren().clear();
        profileCheckboxes = new CheckBox[profileFiles.length];

        for (int i = 0; i < profileFiles.length; i++){
            CheckBox profileCheckBox = new CheckBox(profileFiles[i]);
            profileCheckBox.setSelected(true);
            profileCheckboxes[i] = profileCheckBox;
            ProfileSelectorPane.getChildren().add(profileCheckBox);
        }
        AnalyzeButton.disableProperty().setValue(false);
    }

    @FXML
    void AnalyzeProfile(ActionEvent event) {
        for(int i = 0; i < profileFiles.length; i++) {
            if(profileCheckboxes[i].isSelected()) {
                HashSet<String> parsedProfile = parser.parseProfile(profileFiles[i]);
                analyzer.analyzeProfile(parser.parseProfile(profileFiles[i]));
            }
        }
        printCommonWords(analyzer.getCommonWords(3));
    }

    void printCommonWords(List<String> commonWords){
        CommonWordsTextArea.clear();
        for(String s: commonWords){
            CommonWordsTextArea.appendText(s + "\n");
        }
    }
}
