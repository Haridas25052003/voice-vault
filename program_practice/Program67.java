public class Program67 {
    public static void main(String[] args) {
        
        String s="java programming ";
        int count=0;

       for(int i=0;i<s.length()-1;i++){
           char ch=s.charAt(i);
           if(ch=='a' || ch=='e' || ch=='i' || ch=='o' || ch=='e'){
              count++;
           }
        }
        System.out.println(count);
    }
}
