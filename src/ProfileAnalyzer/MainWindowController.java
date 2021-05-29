package ProfileAnalyzer;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.*;

public class MainWindowController {
    @FXML
    private Button AnalyzeButton;
    @FXML
    private ChoiceBox<Integer> LimitSelector;
    @FXML
    private VBox ProfileSelectorPane;
    @FXML
    private TextArea CommonWordsTextArea;
    @FXML
    private Button LoadProfilesButton;
    @FXML
    private TextField DirectoryField;
    @FXML
    private Button DirectoryButton;
    @FXML
    private TextField StatusIndicator;

    String directory = "C:\\Users\\Tristan_Nibbe\\Downloads\\Reference_Profiles\\";
    int limit = 0;

    Parser parser = new Parser(directory);
    Analyzer analyzer = new Analyzer();

    String[] profileFiles;
    CheckBox[] profileCheckboxes;

    public void initialize() {
        addTooltipToLimitField();
        loadDirectoryField();
        checkForIrrelevantWordsFile();
    }

    @FXML
    void OpenDirectoryExplorer(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showOpenDialog(DirectoryButton.getScene().getWindow());

        String path = file.getAbsolutePath();
        String fileName = file.getName();

        directory = file.getAbsolutePath().substring(0,path.length()-fileName.length());
        loadDirectoryField();
    }

    @FXML
    void LoadProfiles(ActionEvent event) {
        File directoryPath = new File(directory);
        profileFiles = cleanProfileFiles(directoryPath);
        ProfileSelectorPane.getChildren().clear();
        profileCheckboxes = new CheckBox[profileFiles.length];

        for (int i = 0; i < profileFiles.length; i++){
            CheckBox profileCheckBox = new CheckBox(profileFiles[i]);
            profileCheckBox.setSelected(true);
            profileCheckboxes[i] = profileCheckBox;
            ProfileSelectorPane.getChildren().add(profileCheckBox);
        }
        loadLimitChoices();
    }

    @FXML
    void AnalyzeProfile(ActionEvent event) {
        //TODO analysis not returning expected results
        analyzer.clearCommonWords();

        for(int i = 0; i < profileFiles.length; i++) {
            if(profileCheckboxes[i].isSelected()) {
                HashSet<String> parsedProfile = parser.parseProfile(profileFiles[i]);
                analyzer.analyzeProfile(parsedProfile);
            }
        }
        printCommonWords(analyzer.getCommonWords(limit));
    }

    void checkForIrrelevantWordsFile(){
            File file = new File(directory + "\\" + "IrrelevantWords.txt");
            if(!file.exists()) {
                reportError("ERROR: please ensure IrrelevantWords.txt is in your reference profiles directory and the directory is correctly configured in settings");
                disableAnalysis();
            }else {
                reportSuccess("IrrelevantWords.txt successfully detected in directory");
                enableAnalysis();
            }

    }

    void loadDirectoryField(){
        DirectoryField.clear();
        DirectoryField.appendText(directory);
        checkForIrrelevantWordsFile();
        //TODO verify that there are files in the directory besides the irrelevant words file
    }

    String[] cleanProfileFiles(File file){
        String[] fullFileList = file.list();
        String[] profileArr = new String[fullFileList.length - 1];

        for(int i = 0, j = 0; i < fullFileList.length; i++){
            if(!fullFileList[i].equals("IrrelevantWords.txt")) {
                profileArr[j] = fullFileList[i];
                j++;
            }
        }
        return  profileArr;
    }

    void loadLimitChoices(){
        ObservableList<Integer> limiterChoices = FXCollections.observableArrayList();
        LimitSelector.disableProperty().setValue(false);

        for(int i = 0; i < profileCheckboxes.length; i++){
          limiterChoices.add(i+1);
        }

        LimitSelector.setItems(limiterChoices);
        setLimitListener();
    }

    void setLimitListener(){
        LimitSelector.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

            public void changed(ObservableValue ov, Number value, Number new_value) {
                limit = LimitSelector.getItems().get(new_value.intValue());
                AnalyzeButton.disableProperty().setValue(false);
            }
        });
    }

    void printCommonWords(List<String> commonWords){
        CommonWordsTextArea.clear();
        for(String s: commonWords){
            CommonWordsTextArea.appendText(s + "\n");
        }
    }

    void reportError(String error){
        StatusIndicator.setText(error);
    }

    void reportSuccess(String message){
        StatusIndicator.setText(message);
    }

    void disableAnalysis(){
        LoadProfilesButton.disableProperty().setValue(true);
        AnalyzeButton.disableProperty().setValue(true);
        LimitSelector.disableProperty().setValue(true);
    }

    void enableAnalysis(){
        LoadProfilesButton.disableProperty().setValue(false);
    }

    void addTooltipToLimitField(){
        final Tooltip tooltip = new Tooltip();
        tooltip.setText(
                "Minimum number of profiles a word should appear in before it can be counted as a \"common word\"."
        );
        LimitSelector.setTooltip(tooltip);
    }
}
