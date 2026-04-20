public class Program52 {
    public static void main(String[] args) {
        
        int []a={5,2,9,1,7};
        int max=a[0];
        int min=a[0];

        for(int i=0;i<a.length;i++){
            if(a[i]>max){
                max=a[i];
            }
            if(a[i]<min){
                min=a[i];
            }
        }
        System.out.println(max);
        System.out.println(min);
    }
}
