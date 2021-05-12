package ProfileAnalyzer;

public class CommonWord implements Comparable{
    public int count;
    public String word;



    public boolean equals(Object obj){
        boolean equal = false;

        if(obj instanceof CommonWord){
            CommonWord other = (CommonWord) obj;

            if(other.word.equals(this.word)){
                equal = true;
            }
        }


        return equal;
    }

    @Override
    public int compareTo(Object o) {
        if(!(o instanceof CommonWord other)){
            System.out.println("ERROR: Attempting to compare non instance of CommonWord to CommonWord!");
            return 0;
        }

        return other.count - this.count;
    }
}

