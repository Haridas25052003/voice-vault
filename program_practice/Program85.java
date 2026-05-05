
import java.util.HashSet;

class Program85
{
    public static void main(String[] args) {
        
        int []a={10,10,10,20,20,30,40,40,40,50,50};

        HashSet set=new HashSet<>();
        for(int i=0;i<a.length;i++){
            set.add(a[i]+" ");
        }
        System.out.println(set);

    }
}