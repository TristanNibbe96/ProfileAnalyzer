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

    }

    private  void processNewProfile(HashSet<String> profile){

    }

    public void printCommonWords(int limit){

    }
}
