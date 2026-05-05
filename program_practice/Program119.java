public class Program119 {
    public static void main(String[] args) {
        
        int arr[]={1,2,4,5};
        int n=5;

        int sum=0;
        int totalSum=n*(n+1)/2;
        for(int i=0;i<n-1;i++){
            sum+=arr[i];
        }
        System.out.println(totalSum-sum);
    }
}
