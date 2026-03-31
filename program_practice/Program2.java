public class Program2 {
    public static void main(String[] args) {
        
        Program2 p=new Program2();
        int a[]={10,20,30,40,50};
        int secondMax=p.findSecondMax(a);
        System.out.println(secondMax);
    }
    int findSecondMax(int a[]){
        int max=a[0];
        int secondMax=a[0];
        for(int i=0;i<a.length;i++){
            if(a[i]>0){
                secondMax=max;
                max=a[i];
            }
            else if(a[i]>secondMax && a[i]!=max){
                secondMax=a[i];
            }
        }
        return  secondMax;
    }
}
