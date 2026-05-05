public class Program84 {
    public static void main(String[] args) {
        
        int a[]={50,10,40,30,20};
        for(int j=0;j<a.length-1;j++){
        for(int i=0;i<a.length-1;i++){
            if(a[i]>a[i+1]){
                int temp=a[i];
                a[i]=a[i+1];
                a[i+1]=temp;
            }
        }
    }

        for(int i=0;i<a.length;i++){
            System.out.print(a[i]+" ");
        }
    }
}
