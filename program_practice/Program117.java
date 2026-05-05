class Program117
{
    public static void main(String[]args){

        int arr[]={1,4,2,3,7};
        int target=5;
        for(int i=0;i<arr.length-1;i++){
            if(arr[i]+arr[i+1]==target){
                System.out.println(i+" "+(i+1));
                break;
            }
        }
    }
}