class Program20 {
    public static void main(String[]args){

        Program20 p=new Program20();
        int result=p.fib(5);
        System.out.println(result);

        int result2=p.fact(5);
        System.out.println(result2);
    }

    int fib(int n){
        if(n==0) return 0;
        if(n==1) return 1;

        return fib(n-1)+fib(n-2);
    }

    int fact(int n){
        if(n==0) return 1;

        return n*fact(n-1);
    }

    void  sum(int n){
        if(n==0) return ;

        System.out.print(n);
        sum(n-1);
    }

    void sum2(int n){
        if(n==0) return;

        sum2(n-1);
        System.out.print(n);
    }
}
