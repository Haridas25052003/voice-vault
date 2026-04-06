public class Program28 {
    public static void main(String[] args) {

        // bubble sorting
        /*
         * int a[]={20,40,10,50,30};
         * int n=a.length;
         * for(int i=0;i<n-1;i++){
         * for(int j=0;j<n-1;j++){
         * if(a[j]>a[j+1]){
         * int temp=a[j];
         * a[j]=a[j+1];
         * a[j+1]=temp;
         * }
         * }
         * }
         * for(int i=0;i<a.length;i++){
         * System.out.print(a[i]+" ");
         * }
         */

        /*
         * int a[]={50,20,40,10,30};
         * int n=a.length;
         * for(int i=0;i<n-1;i++){
         * for(int j=0;j<n-1;j++){
         * if(a[j]>a[j+1]){
         * int temp=a[j];
         * a[j]=a[j+1];
         * a[j+1]=temp;
         * }
         * }
         * }
         * for(int i=0;i<n;i++){
         * System.out.print(a[i]+" ");
         * }
         */

        /*
         * int a[]={60,20,30,10,40,50};
         * int n=a.length;
         * for(int i=0;i<n-1;i++){
         * for(int j=0;j<n-1;j++){
         * if(a[j]>a[j+1]){
         * int temp=a[j];
         * a[j]=a[j+1];
         * a[j+1]=temp;
         * }
         * }
         * }
         * for(int i=0;i<n;i++){
         * System.out.print(a[i]+" ");
         * }
         */

        /*
         * int a[]={20,40,10,50,30};
         * int n=a.length;
         * for(int i=0;i<n-1;i++){
         * for(int j=0;j<n-1;j++){
         * if(a[j]>a[j+1]){
         * int temp=a[j];
         * a[j]=a[j+1];
         * a[j+1]=temp;
         * }
         * }
         * }
         * for(int i=0;i<n;i++){
         * System.out.print(a[i]+" ");
         * }
         */
        /*
         * int a[]={50,20,40,10,30};
         * int n=a.length;
         * for(int i=0;i<n-1;i++){
         * for(int j=0;j<n-1;j++){
         * if(a[j]>a[j+1]){
         * int temp=a[j];
         * a[j]=a[j+1];
         * a[j+1]=temp;
         * }
         * }
         * }
         * for(int i=0;i<a.length;i++){
         * System.out.print(a[i]+" ");
         * }
         */

        /*
         * int a[]={50,20,30,10,40};
         * int n=a.length;
         * for(int i=0;i<n-1;i++){
         * for(int j=0;j<n-1;j++){
         * if(a[j]>a[j+1]){
         * int temp=a[j];
         * a[j]=a[j+1];
         * a[j+1]=temp;
         * 
         * }
         * }
         * }
         * for(int i=0;i<a.length;i++){
         * System.out.print(a[i]+" ");
         * }
         * 
         * int b[]={66,78,98,23,11,56};
         * int m=b.length;
         * for(int i=0;i<n-1;i++){
         * for(int j=0;j<n-1;j++){
         * if(b[i]>b[i+1]){
         * int temp=b[i];
         * b[i]=b[i+1];
         * b[i+1]=temp;
         * }
         * }
         * }
         * for(int i=0;i<b.length;i++){
         * System.out.print(b[i]+" ");
         * }
         */

        /*
         * int[] a={50,40,10,30,20};
         * int n=a.length;
         * for(int i=0;i<n-1;i++){
         * for(int j=0;j<n-1;j++){
         * if(a[j]>a[j+1]){
         * int temp=a[j];
         * a[j]=a[j+1];
         * a[j+1]=temp;
         * }
         * }
         * }
         * for(int i=0;i<n;i++){
         * System.out.print(a[i]+" ");
         * }
         */

        /*
         * int a[]={65,45,23,76,89,12};
         * int n=a.length;
         * for(int i=0;i<n-1;i++){
         * for(int j=0;j<n-1;j++){
         * if(a[j]>a[j+1]){
         * int temp=a[j];
         * a[j]=a[j+1];
         * a[j+1]=temp;
         * }
         * }
         * }
         * for(int i=0;i<n;i++){
         * System.out.print(a[i]+" ");
         * }
         */

        int a[]={60,40,30,10,20,50};
        int n=a.length;
        for(int i=0;i<n-1;i++){
            for(int j=0;j<n-1;j++){
                if(a[j]>a[j+1]){
                    int temp=a[j];
                    a[j]=a[j+1];
                    a[j+1]=temp;
                }
            }
        }
        for(int i=0;i<a.length;i++){
            System.out.print(a[i]+" ");
        }

    }
}
