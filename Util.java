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
}
