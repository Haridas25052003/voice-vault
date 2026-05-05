
import java.util.HashSet;

public class Program94 {
    public static void main(String[] args) {
        
        int arr[]={1,1,2,2,3,3,4,5};
        HashSet set=new HashSet<>();
        for(int i=0;i<arr.length;i++){
            set.add(arr[i]);
        }
        System.out.println(set);
    }
}
