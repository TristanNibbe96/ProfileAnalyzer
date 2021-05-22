package ProfileAnalyzer;

import ProfileAnalyzer.Analyzer;
import ProfileAnalyzer.Parser;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.util.*;

public class MainWindowController extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
        primaryStage.setTitle("Profile Analyzer");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }

    public static void main(String[] args) {
        String directory = "C:\\Users\\Tristan_Nibbe\\Downloads";

        Parser parser = new Parser(directory);
        Analyzer analyzer = new Analyzer();

        File directoryPath = new File(directory + "\\Reference_Profiles");
        String contents[] = directoryPath.list();

        for(String file: contents) {
            HashSet<String> parsedProfile = parser.parseProfile(file);

            analyzer.analyzeProfile(parser.parseProfile(file));
        }

        analyzer.printCommonWords(3);
    }
}
