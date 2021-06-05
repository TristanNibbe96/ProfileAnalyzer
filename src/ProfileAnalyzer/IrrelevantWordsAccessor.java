package ProfileAnalyzer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class IrrelevantWordsAccessor {
    public boolean saveIrrelevantWords(List<String> words, String directory){
        boolean savedSucessfully = true;

        try {
            FileWriter myWriter = new FileWriter(directory + "irrelevantWords.txt");
            for (String word: words){
                myWriter.write(word + "\n");
            }
            myWriter.close();
        } catch (IOException e) {
            savedSucessfully = false;
            System.out.println("Unable to write to irrelevantWords file.");
        }

        return savedSucessfully;
    }

    public List<String> loadIrrelevantWords(String directory) throws FileNotFoundException {
        List<String> irrelevantWords = new ArrayList<>();
        File irrelevantWordsFile = new File(directory + "\\IrrelevantWords.txt");
        BufferedReader br = new BufferedReader(new FileReader(irrelevantWordsFile));

        try {
            String word;
            while ((word = br.readLine()) != null)
                irrelevantWords.add(word);
        }
        catch (IOException e){
            System.out.println("IOException");
        }

        return irrelevantWords;
    }
}
