import java.util.HashSet;

public class Program37 {
    public static void main(String[] args) {
        int []a={1,2,3,2,3,4,4,5};
        HashSet<Integer> set=new HashSet<>();
        for(int i=0;i<a.length;i++){
            set.add(a[i]);
        }
        System.out.println(set);
    }
}