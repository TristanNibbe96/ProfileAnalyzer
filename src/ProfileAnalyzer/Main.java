import ProfileAnalyzer.Analyzer;
import ProfileAnalyzer.Parser;

import java.io.File;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        String directory = "C:\\Users\\Tristan_Nibbe\\Downloads";

        Parser parser = new Parser(directory);
        Analyzer analyzer = new Analyzer();

        File directoryPath = new File(directory + "\\Reference_Profiles");
        String contents[] = directoryPath.list();



    }
}
