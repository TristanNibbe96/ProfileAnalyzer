package ProfileAnalyzer;

import java.io.*;
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
        File irrelevantWordsFile = new File(directory + "\\IrrelevantWords.txt");
        BufferedReader br = new BufferedReader(new FileReader(irrelevantWordsFile));

        try {
            String word;
            while ((word = br.readLine()) != null)
                irrelevantWords.add(word);
        }
        catch (IOException e){
            System.out.println("exceptionIO");
        }
    }


    public HashSet<String> parseProfile(String fileName){
        String path = directory + "\\" + "Reference_Profiles" + "\\" + fileName;

        String profile = importProfile(path);
        String processedProfile = processProfile(profile);
        HashSet<String> choppedProfile = segmentProfile(processedProfile);
        removeIrrelevantWords(choppedProfile);

        return choppedProfile;
    }

    private String importProfile(String path){
        File file = new File(path);
        String profileText = "";


        return profileText;
    }

    private String processProfile(String rawProfile){
        return "";
    }

    private HashSet<String> segmentProfile(String profile){

        return new HashSet<>();
    }

    private void removeIrrelevantWords(HashSet<String> profile){

    }


}
