public class Program38 {
    public static void main(String[] args) {
        
        String s="java programming";
        String rev="";
        for(int i=s.length()-1;i>=0;i--){
            rev+=s.charAt(i);
        }
        //System.out.println(rev);
        if(s.equals(rev)){
            System.out.println("palindrome ");
        }
        else{
            System.out.println("not palindrome");
        }
    }
}
