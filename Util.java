import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Util {
    private Util(){}

    public static class Pair<T1,T2>{
        public final T1 first;
        public final T2 second;
        public Pair(T1 first, T2 second){
            this.first = first;
            this.second = second;
        }
        public String toString(){return "Pair(" + first + ", " + second + ")";}
    }
    public static <T1,T2> Map<T1,T2> toMap(Object[][] a){
        return Stream.of(a).collect(Collectors.toMap(data -> (T1) data[0], data -> (T2) data[1]));
    }
}
