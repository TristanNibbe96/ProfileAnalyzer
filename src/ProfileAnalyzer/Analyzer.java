package ProfileAnalyzer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class Analyzer {
    private LinkedList<CommonWord> commonWords;

    public void analyzeProfile(HashSet<String> profile){
        if (commonWords == null){
            setupNewCommonWords(profile);
        }else {
            processNewProfile(profile);
        }
    }

    public void removeWordsFromCommonWordsList(List<CommonWord> wordsToRemove){
        for(CommonWord word: wordsToRemove){
            commonWords.remove(word);
        }
    }

    private void setupNewCommonWords(HashSet<String> profile){
        commonWords = new LinkedList<>();

        for(String s: profile){
            commonWords.add(new CommonWord(s));
        }
    }

    public void clearCommonWords(){
        commonWords = null;
    }

    private  void processNewProfile(HashSet<String> profile){
        for (String s: profile){

            CommonWord word = new CommonWord(s);

            if(commonWords.contains(word)){
                CommonWord oldWord = commonWords.get(commonWords.indexOf(word));
                oldWord.incrementCount();
            }else{
                commonWords.add(new CommonWord(s));
            }
        }
    }

    public List<CommonWord> getRawCommonWords(int limit){
        commonWords.sort(null);

        List<CommonWord> commonWordsRaw = new ArrayList<>();

        for (CommonWord word: commonWords){
            if(word.getCount() >= limit) {
                commonWordsRaw.add(word);
            }
        }

        return commonWordsRaw;
    }

    public List<String> getCommonWords(int limit){
        commonWords.sort(null);

        List<String> commonWordsString = new ArrayList<>();

        for (CommonWord word: commonWords){
            if(word.getCount() >= limit) {
                commonWordsString.add(word.toString());
            }
        }

        return commonWordsString;
    }
}
