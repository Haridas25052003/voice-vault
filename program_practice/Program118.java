public class Program118 {
    public static void main(String[] args) {
        
        int a[]={1,2,3,4};
        int b[]={5,6,7,8};

        int result[]=new int[a.length+b.length];

        for(int i=0;i<a.length;i++){
            result[i]+=a[i];
        }

        for(int i=0;i<b.length;i++){
            result[a.length+i]+=b[i];
        }

        for(int i=0;i<result.length;i++){
            System.out.print(result[i]+" ");
        }
    }
}
