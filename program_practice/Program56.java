class Program56
{
    public static void main(String[] args) {
        
        int num=12345;
        int rev=0;

        while(num!=0){
            rev*=10;
            rev+=num%10;
            num/=10;
        }

    }
}