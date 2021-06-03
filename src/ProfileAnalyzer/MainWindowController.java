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
    private Button LoadProfilesButton;
    @FXML
    private TextField DirectoryField;
    @FXML
    private Button DirectoryButton;
    @FXML
    private TextArea StatusIndicator;
    @FXML
    private VBox IrrelevantWordsDisplayArea;
    @FXML
    private VBox CommonWordsDisplayArea;

    String directory = "C:\\Users\\Tristan_Nibbe\\Downloads\\Reference_Profiles\\";
    int limit = 0;

    Parser parser = new Parser(directory);
    Analyzer analyzer = new Analyzer();

    String[] profileFiles;
    CheckBox[] profileCheckboxes;
    CheckBox[] commonWordsCheckboxes;
    CheckBox[] irrelevantWordsCheckboxes;

    public void initialize() {
        addTooltipToLimitField();
        loadDirectoryField();
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
        analyzer.clearCommonWords();

        for(int i = 0; i < profileFiles.length; i++) {
            if(profileCheckboxes[i].isSelected()) {
                HashSet<String> parsedProfile = parser.parseProfile(profileFiles[i]);
                analyzer.analyzeProfile(parsedProfile);
            }
        }
        printCommonWords(analyzer.getCommonWords(limit));
    }

    @FXML
    void SetDirectoryFromField(ActionEvent event) {
        directory = DirectoryField.getText();
        checkIfDirectoryIsValid();
    }

    @FXML
    void ExportCommonWords(ActionEvent event) {
        //TODO enable export of common words
    }

    @FXML
    void RemoveCommonWords(ActionEvent event) {
        //TODO enable deletion of common words
    }

    @FXML
    void RemoveIrrelevantWords(ActionEvent event) {
        //TODO enable deletion of irrelevant words
    }

    @FXML
    void SaveIrrelevantWords(ActionEvent event) {
        //TODO enable saving of irrelevant words
    }

    @FXML
    void SelectAllCommonWords(ActionEvent event) {

    }

    @FXML
    void SelectAllIrrelevantWords(ActionEvent event) {

    }

    @FXML
    void DeselectAllCommonWords(ActionEvent event) {

    }

    @FXML
    void DeselectAllIrrelevantWords(ActionEvent event) {

    }

    void checkIfDirectoryIsValid(){
        StatusIndicator.clear();
        if(checkNumberOfProfiles() && checkForIrrelevantWordsFile() && checkTypeOfProfiles()){
            enableAnalysis();
        }else {
            disableAnalysis();
        }

    }

    boolean checkNumberOfProfiles(){
        File file = new File(directory);
        boolean directoryValid = false;

        try {
            if (file.list().length > 1) {
                directoryValid = true;
                reportSuccess("Correct number of profiles detected");
            }
        }catch (NullPointerException e){
            reportError("ERROR: Null pointer to profile directory");
        }

        return directoryValid;
    }

    boolean checkTypeOfProfiles(){
        File file = new File(directory);
        boolean directoryValid = true;

        try {
            for(String fileName: file.list()){
                String fileExtension = fileName.substring(fileName.length() - 4, fileName.length());

                if(!fileExtension.equals(".pdf") && !fileExtension.equals(".txt")){
                    directoryValid = false;
                    reportError("ERROR: incorrect file type in directory");
                }
            }
        }catch (NullPointerException e){
            directoryValid = false;
            reportError("ERROR: Null pointer to profile directory");
        }

        if(directoryValid){
            reportSuccess("all files in directory are of a compatible type");
        }

        return directoryValid;
    }

    void printIrrelevantWords(){
        IrrelevantWordsDisplayArea.getChildren().clear();
        List<String> irrelevantWords = parser.getIrrelevantWords();
        irrelevantWordsCheckboxes = new CheckBox[irrelevantWords.size()];

        int i = 0;
        for(String s: irrelevantWords){
            CheckBox newIrrelevantWord = new CheckBox(s);
            newIrrelevantWord.setSelected(true);
            IrrelevantWordsDisplayArea.getChildren().add(newIrrelevantWord);
            irrelevantWordsCheckboxes[i] = newIrrelevantWord;
            i++;
        }
    }

    boolean checkForIrrelevantWordsFile(){
        File file = new File(directory + "\\" + "IrrelevantWords.txt");
        boolean directoryValid = false;

        if(!file.exists()) {
            reportError("ERROR: please ensure IrrelevantWords.txt is in your reference profiles directory and the directory is correctly configured in settings");
        }else {
            reportSuccess("IrrelevantWords.txt successfully detected in directory");
            directoryValid = true;
        }

        return directoryValid;
    }

    void loadDirectoryField(){
        DirectoryField.clear();
        DirectoryField.appendText(directory);
        checkIfDirectoryIsValid();
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
        CommonWordsDisplayArea.getChildren().clear();

        commonWordsCheckboxes = new CheckBox[commonWords.size()];
        int i = 0;

        for(String s: commonWords){
            CheckBox newCommonWord = new CheckBox(s);
            newCommonWord.setSelected(true);
            CommonWordsDisplayArea.getChildren().add(newCommonWord);
            commonWordsCheckboxes[i] = newCommonWord;
            i++;
        }
    }

    void reportError(String error){
        StatusIndicator.appendText(error + "\n");
    }

    void reportSuccess(String message){
        StatusIndicator.appendText(message + "\n");
    }

    void disableAnalysis(){
        LoadProfilesButton.disableProperty().setValue(true);
        AnalyzeButton.disableProperty().setValue(true);
        LimitSelector.disableProperty().setValue(true);
    }

    void enableAnalysis(){
        LoadProfilesButton.disableProperty().setValue(false);
        parser.changeDirectory(directory);
        printIrrelevantWords();
    }

    void addTooltipToLimitField(){
        final Tooltip tooltip = new Tooltip();
        tooltip.setText(
                "Minimum number of profiles a word should appear in before it can be counted as a \"common word\"."
        );
        LimitSelector.setTooltip(tooltip);
    }
}
