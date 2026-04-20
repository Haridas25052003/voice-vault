public class Program48 {
    public static void main(String[] args) {
        
        int n=10;
        int a=0, b=1;

        System.out.println(a);
        System.out.println(b);
        for(int i=0;i<n;i++){
            int c=a+b;
            a=b;
            b=c;
            System.out.println(c);
        }
    }
}
