public class Program61 {
    public static void main(String[] args) {
        
        int []arr={10,20,30,40,50};
        int max=arr[0];
        int secondmax=arr[0];

        for(int i=0;i<arr.length;i++){
            if(arr[i]>max){
                secondmax=max;
                max=arr[i];
            }
            else if(arr[i]>secondmax && arr[i]!=0){
                secondmax=arr[i];
            }
        }

        System.out.println(max);
        System.out.println(secondmax);
    }
}
