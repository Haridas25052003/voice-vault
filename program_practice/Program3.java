public class Program3 {
    public static void main(String[] args) {
        
        int a[]={10,20,30,40,50};
        int start=a[0];
        int end=a[a.length-1];

        while(start < end){
            int temp=start;
            start=end;
            end=temp;
        }
    

    for(int i=0;i<a.length;i++){
        System.out.print(a[i]+"");
    }
}
}
