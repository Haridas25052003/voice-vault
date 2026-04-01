class Program18{
    public static void main(String[] args) {
        
        Program18 p=new Program18();
        int result=p.factorial(5);
        System.out.println(result);
        
    }
    int factorial(int n){
        if(n==0 || n==1){
            return 1;
        }
        else{
            return n*factorial(n-1);
        }
    }
}