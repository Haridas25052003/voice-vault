public class Program79 {
    public static void main(String[] args) {
        
        System.out.println(isPrime(2));
    }

    static boolean isPrime(int num){
        if(num<=1){
            return false;
        }
        for(int i=2;i<=num/2;i++){ 
            if(num%i==0){
                return false;
            }
        }
        return true;
    }


}
