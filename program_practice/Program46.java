public class Program46 {
    public static void main(String[] args) {
        
        // int num=153;
        // int temp=num;
        // int sum=0;
        // while(num!=0){
        //     int digit=num%10;
        //     sum+=digit*digit*digit;
        //     num/=10;
        // }
        // if(sum==temp){
        //     System.out.println("armstrong");
        // }
        // else{
        //     System.out.println("not armstrong");
        // }
        int num=153;
        int temp=num;
        int sum=0;
        while(num!=0){
            int digit=num%10;
            sum+=digit*digit*digit;
            num/=10;
        }
        if(sum==temp){
            System.out.println("armstrong");
        }
        else{
            System.out.println("not armstrong");
        }
    }
}
