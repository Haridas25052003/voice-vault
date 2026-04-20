
import java.util.HashSet;

public class Program54 {
    public static void main(String[] args) {
        
        int a[]={1,1,2,2,3,3,4,5,5};
        HashSet set=new HashSet<>();

        for(int i=0;i<a.length;i++){
            set.add(a[i]);
        }

        System.out.println(set);
    }
}
