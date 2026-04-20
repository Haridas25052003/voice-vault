public class Progrm74 {
    public static void main(String[] args) {
        
        int a=0, b=1;
        int n=10;
        System.out.println(a);
        System.out.println(b);
        for(int i=0;i<n;i++){
            int c=a+b;
            System.out.println(c);
            a=b;
            b=c;
        }
    }
}
