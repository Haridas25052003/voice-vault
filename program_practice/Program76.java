public class Program76 {
    public static void main(String[] args) {
        
        int num=12345;
        int temp=num;
        int rev=0;

        while(num!=0){
            rev*=10;
            rev+=num%10;
            num/=10;
        }
        if(temp==rev){
            System.out.println("palindrome");
        }
        else{
            System.out.println("not palindrome");
        }
    }
}
