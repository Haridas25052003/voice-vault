class Program53
{
    public static void main(String[] args) {
        
        int []arr={1,5,9,10,11};

        int max=arr[0];
        int secondmax=arr[0];

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