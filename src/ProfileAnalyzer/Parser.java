package ProfileAnalyzer;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class Parser {

    private List<String> irrelevantWords;
    private String directory;

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
        String path = directory + fileName;

        String profile = importProfile(path);
        String processedProfile = processProfile(profile);
        HashSet<String> segmentedProfile = segmentProfile(processedProfile);
        removeIrrelevantWords(segmentedProfile);

        return segmentedProfile;
    }

    public List<String> getIrrelevantWords(){
        return irrelevantWords;
    }

    private String importProfile(String path){
        File file = new File(path);
        String profileText = "";

        try {
            PDDocument document = PDDocument.load(file);
            PDFTextStripper pdfStripper = new PDFTextStripper();

            profileText = pdfStripper.getText(document);
            document.close();
        }
        catch(IOException e){
            System.out.println("ERROR: Profile Not Found");
        }

        return profileText;
    }

    private String processProfile(String rawProfile){
        String processedProfile = rawProfile.replaceAll("[\\d|·|(|)]|&nbsp", "");

        processedProfile = processedProfile.replaceAll("[\r|\n|\\-|.|,|/|_|·]", " ");
        processedProfile = processedProfile.replace("\u00a0","");
        processedProfile = processedProfile.replaceAll("[\s]{2,}"," ");
        processedProfile = processedProfile.toLowerCase();

        return processedProfile;
    }

    private HashSet<String> segmentProfile(String profile){
        String[] delimitedProfile = profile.split(" ");
        List<String> profileList = new ArrayList<>(Arrays.asList(delimitedProfile));
        HashSet<String> segmentedProfile = new HashSet<>();

        for(String s: profileList){
            segmentedProfile.add(s);
        }

        return segmentedProfile;
    }

    private void removeIrrelevantWords(HashSet<String> profile){
        for(String s: irrelevantWords) {
            profile.remove(s);
        }
    }


}
