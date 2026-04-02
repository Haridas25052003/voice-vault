public class Program27 {
    public static void main(String[] args) {

        Program27 p = new Program27();
        int a[] = { 10, 20, 30, 40, 50 };
        int key = 40;
        int result = p.binarySearch(a, key);
        System.out.println(result);
    }

    /*
     * int binarySearch(int a[],int key){
     * int low=0;
     * int high=a.length-1;
     * while(low<=high){
     * int mid=(low+high)/2;
     * if(a[mid]==key){
     * return mid;
     * }
     * else if(a[mid]<key){
     * low=mid+1;
     * }
     * else{
     * high=mid-1;
     * }
     * }
     * return -1;
     * }
     */

    /*
     * int binarySearch(int a[],int key){
     * int low=0;
     * int high=a.length-1;
     * while(low<=high){
     * int mid=(low+high)/2;
     * if(a[mid]==key){
     * return mid;
     * }
     * else if(a[mid]<key){
     * low=mid+1;
     * }
     * else{
     * high=mid-1;
     * }
     * 
     * }
     * return -1;
     * }
     */

    /*
     * int binarySearch(int a[], int key) {
     * int low = 0;
     * int high = a.length - 1;
     * while (low <= high) {
     * int mid = (low + high) / 2;
     * if (a[mid] == key) {
     * return mid;
     * } else if (a[mid] < key) {
     * low = mid + 1;
     * } else {
     * high = mid - 1;
     * }
     * }
     * return -1;
     * }
     */

    int binarySearch(int a[],int key){
        int low=0;
        int high=a.length-1;
        while(low<=high){
            int mid=(low+high)/2;
            if(a[mid]==key){
                return mid;
            }
            else if(a[mid]<key){
                low=mid+1;
            }
            else{
                high=mid-1;
            }
        }
        return -1;
    }
}
