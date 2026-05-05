public class Program86 {
    public static void main(String[] args) {
        
        int []a={1,2,4,5};
        int n=5;
        int totalsum=n*(n+1)/2;
        int sum=0;

        for(int i=0;i<a.length;i++){
            sum+=a[i];
        }
        System.out.println(totalsum-sum);
    }
}
