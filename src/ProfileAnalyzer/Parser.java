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
    private final IrrelevantWordsAccessor wordsAccessor;

    public Parser(String directory){
        this.directory = directory;
        this.wordsAccessor = new IrrelevantWordsAccessor();
        setUpIrrelevantWords();
    }

    public void setUpIrrelevantWords() {
        try{
            irrelevantWords = wordsAccessor.loadIrrelevantWords(directory);
        }
        catch (FileNotFoundException e){
            System.out.println("No File with irrelevant words fpund");
        }
    }

    public void changeDirectory(String directory){
        this.directory = directory;
        setUpIrrelevantWords();
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

    public void addWordsToIrrelevantList(List<String> wordsToAdd){
        irrelevantWords.addAll(wordsToAdd);
    }

    public void removeWordsFromIrrelevantList(List<String> wordsToRemove){
        for(String word: wordsToRemove){
            irrelevantWords.remove(word);
        }
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
            if(s.length() > 1) {
                segmentedProfile.add(s);
            }
        }

        return segmentedProfile;
    }

    private void removeIrrelevantWords(HashSet<String> profile){
        for(String s: irrelevantWords) {
            profile.remove(s);
        }
    }


}
