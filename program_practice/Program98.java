import java.util.ArrayList;
import java.util.HashSet;

public class Program98 {
    public static void main(String[] args) {
        
        int arr[]={1,2,1,2,3,4,5};
        HashSet set=new HashSet<>();
        ArrayList list=new ArrayList<>();


        int x[]=new int[arr.length];
        for(int i=0;i<x.length;i++){
            if(!set.add(x[i])){
                list.add(x[i]);
            }
        }

        System.out.println(list);

    }
}
