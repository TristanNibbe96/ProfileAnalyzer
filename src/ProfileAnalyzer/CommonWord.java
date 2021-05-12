package ProfileAnalyzer;

public class CommonWord implements Comparable{
    public int count;

    @Override
    public int compareTo(Object o) {
        if(!(o instanceof CommonWord other)){
            System.out.println("ERROR: Attempting to compare non instance of CommonWord to CommonWord!");
            return 0;
        }

        return other.count - this.count;
    }


}
