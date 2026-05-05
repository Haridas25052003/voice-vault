import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Program120 {
    public static void main(String[] args) {
        
        int []arr={1,1,1,2,2,2,2};
        Set set=new HashSet<>();
        List list=new ArrayList<>();

        for(int i=0;i<arr.length;i++){
            if(!set.add(arr[i]))
                list.add(arr[i]);
        }
        System.out.println(list);
        
    }
}
