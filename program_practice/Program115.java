public class Program115 {
    public static void main(String[] args) {
        
        int arr[]={1,2,3,4,5,6};
        int max=0;
        int secondmax=0;
        for(int i=0;i<arr.length;i++){
            if(arr[i]>max){
                secondmax=max;
                max=arr[i];
            }
            else if(arr[i]>secondmax && arr[i]!=max){
                secondmax=arr[i];
            }
        }
        System.out.println(max);
        System.out.println(secondmax);
    }
}
