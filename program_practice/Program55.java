public class Program55 {
    public static void main(String[] args) {
        
        int []arr={1,2,4,5};
        int n=5;

        int sum=n*(n+1)/2;

        int arrsum=0;

        for(int i=0;i<arr.length;i++){
            arrsum+=arr[i];
        }

        System.out.println(sum-arrsum);

    }
}
