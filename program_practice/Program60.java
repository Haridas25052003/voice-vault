public class Program60 {
    public static void main(String[] args) {
        
        int []arr={10,25,5,40};

        int max=arr[0];
        for(int i=0;i<arr.length;i++){
            if(arr[i]>max){
                max=arr[i];
            }
        }
        System.out.println(max);
    }
}
