package ProfileAnalyzer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class CommonWordsAccessor {

    public void saveCommonWords(List<String> words, String directory){
        createCommonWordsFile(directory);

        try {
            FileWriter myWriter = new FileWriter(directory + "commonWords.txt");
            for (String word: words){
                myWriter.write(word + "\n");
            }
            myWriter.close();
        } catch (IOException e) {
            System.out.println("Unable to write to irrelevantWords file.");
        }
    }

    private void createCommonWordsFile(String directory){
        try {
            File myObj = new File(directory + "commonWords.txt");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("Unable to create commonWords.txt");
            e.printStackTrace();
        }
    }
}


