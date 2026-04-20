public class Program45 {
    public static void main(String[] args) {
        
        int num=123;
        int product=1;

        while(num!=0){
            product*=num%10;
            num/=10;
        }
        System.out.println(product);

    }
}
