public class Program111 {
    public static void main(String[] args) {
        
        int []arr={10,25,5,8,30};
        int key=8;

        boolean found=false;

        for(int i=0;i<arr.length;i++){
            if(arr[i]==key){
                System.out.println("Element found at index"+i);
                found=true;
                break;
            }

        }
        if(!found){
            System.out.println("Element not found");
        }

    }
}
