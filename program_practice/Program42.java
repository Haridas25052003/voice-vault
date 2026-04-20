public class Program42 {
    public static void main(String[] args) {
        
        // int num=121;
        // int temp=num;
        // int rev=0;
        // while(num!=0){
        //     rev*=10;
        //     rev+=num%10;
        //     num/=10;
        // }
        // if(temp==rev){
        //     System.out.println("palindrome");
        // }
        // else{
        //     System.out.println("not palindrome");
        // }

        int num=1221;
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
