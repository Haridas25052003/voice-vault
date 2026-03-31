class Program1 {
    public static void main(String[]args){

        //find the maximun element from the array
        int a[]={10,20,30,40,50};
        Program1 p=new Program1();
        int max=p.findMax(a);
        System.out.println(max);
    }

    int findMax(int a[]){
        int max=a[0];
        for(int i=0;i<a.length;i++){
            if(a[i]>max){
                max=a[i];
            }
        }
        return max;
    }
}