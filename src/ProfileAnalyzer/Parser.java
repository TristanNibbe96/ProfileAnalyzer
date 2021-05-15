package ProfileAnalyzer;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Parser {

    List<String> irrelevantWords;
    String directory;

    public Parser(String directory){
        this.directory = directory;

        irrelevantWords = new ArrayList<>();

        try{setUpIrrelevantWords();}
        catch (FileNotFoundException e){
            System.out.println("No File with irrelevant wprds fpund");
        }
    }

    public void setUpIrrelevantWords() throws FileNotFoundException{

    }

    public void analyzeProfile(){

    }

    public HashSet<String> parseProfile(String fileName){

        return new HashSet<>();
    }

}
