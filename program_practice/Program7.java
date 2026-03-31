public class Program7 {
    public static void main(String[] args) {
        
        Program7 p = new Program7();
        int a[]={10,20,30,40,50};
        int key=30;
        int result=p.linearSearch(a, key);
        System.out.println(result);
    }

    int linearSearch(int a[],int key){
        for(int i=0;i<a.length;i++){
            if(a[i]==key){
                return i;
            }
        }
        return -1;
    }
}
