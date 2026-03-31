public class Program5 {
    public static void main(String[] args) {
        
        boolean isSorted=true;
        int a[]={50,20,10,40,30};
        for(int i=0;i<a.length-1;i++){
            if(a[i]>a[i+1]){
                isSorted=false;
                break;
            }
        }
        System.out.println(isSorted);
    }
}
