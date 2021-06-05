package ProfileAnalyzer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class CommonWordsAccessor {

    public boolean saveCommonWords(List<String> words, String directory){
        boolean successfullySaved = true;

        try {
            FileWriter myWriter = new FileWriter(directory + "commonWords.txt");
            for (String word: words){
                myWriter.write(word + "\n");
            }
            myWriter.close();
        } catch (IOException e) {
            System.out.println("Unable to write to irrelevantWords file.");
            successfullySaved = false;
        }

        return successfullySaved;
    }

    public boolean createCommonWordsFile(String directory){
        boolean succesfullyCreated = false;

        try {
            File myObj = new File(directory + "commonWords.txt");
            if (myObj.createNewFile()) {
                succesfullyCreated = true;
            }
        } catch (IOException e) {
            System.out.println("Unable to create commonWords.txt");
            e.printStackTrace();
        }

        return succesfullyCreated;
    }
}


