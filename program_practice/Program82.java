public class Program82 {
    public static void main(String[] args) {
        
       print(1);
    }

    static void print(int i){
        if(i>100)
            return ;

        System.out.println(i);
        print(i+1);
    }
}
