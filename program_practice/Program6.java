public class Program6 {
    public static void main(String[] args) {
        
        int a[]={0,1,2,0,4,0,5};
        int j=0;
        for(int i=0;i<a.length;i++){
            if(a[i]!=0){
                int temp=a[i];
                a[i]=a[j];
                a[j]=temp;
                j++;
            }
        }
        for(int i=0;i<a.length;i++){
            System.out.print(a[i]+"");
        }
    }
}
