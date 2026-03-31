public class Program8 {
    public static void main(String[] args) {
        
        int a[]={50,30,20,10,40};
        int min=a[0];
        for(int i=0;i<a.length;i++){
            if(a[i]<min){
                min=a[i];
            }
        }
        System.out.println(min);
    }
}
