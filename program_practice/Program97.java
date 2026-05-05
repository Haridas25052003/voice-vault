public class Program97 {
    public static void main(String[] args) {
        
        int arr[]={1,2,4,5};
        int n=5;
        int actualSum=n*(n+1)/2;
        int sum=0;
        for(int i=0;i<n-1;i++){
            sum+=arr[i];
        }
        System.out.println("missing number "+ (actualSum-sum));
    }
}
