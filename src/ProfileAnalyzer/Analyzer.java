package ProfileAnalyzer;

import java.util.HashSet;
import java.util.LinkedList;

public class Analyzer {
    private LinkedList<CommonWord> commonWords;

    public void analyzeProfile(HashSet<String> profile){
        if (commonWords == null){
            setupNewCommonWords(profile);
        }else {
            processNewProfile(profile);
        }
    }

    private void setupNewCommonWords(HashSet<String> profile){
        commonWords = new LinkedList<>();

        for(String s: profile){
            commonWords.add(new CommonWord(s));
        }
    }

    private  void processNewProfile(HashSet<String> profile){
        for (String s: profile){
            CommonWord word = new CommonWord(s);


        }
    }

    public void printCommonWords(int limit){

    }
}
