class Program49{
    public static void main(String[] args) {
        
       int num=1234;
       int max=0;
       while(num!=0){
        int digit=num%10;
        if(digit>max){
            max=digit;
        }
        num/=10;
       }

       System.out.println(max);
    }
}