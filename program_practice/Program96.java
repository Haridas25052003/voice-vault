public class Program96 {
    public static void main(String[] args) {
        
        int [] a={5,4,10};
        int [] b={4,15,16,7};
        int []result=new int[a.length+b.length];

        for(int i=0;i<a.length;i++){
            result[i]+=a[i];
        }
        for(int i=0;i<b.length;i++){
            result[a.length+i]=b[i];
        }
        for(int i=0;i<result.length-1;i++){
            for(int j=0;j<result.length-1;j++){
                if(result[j]>result[j+1]){
                    int temp=result[j];
                    result[j]=result[j+1];
                    result[j+1]=temp;
                }
            }
        }
        for(int i=0;i<result.length;i++){
            System.out.print(result[i]+" ");
        }
    }
}
