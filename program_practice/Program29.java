class Program29
{
    public static void main(String[]args)
    {

        //reverse a string
        String s="hello";
        String rev=" ";
        for(int i=s.length()-1;i>=0;i--){
            rev=rev+s.charAt(i);
        }
        System.out.println(rev);

        if(s.equals(rev)){
            System.out.println("palindrome");
        }
        else{
            System.out.println("not palidrome");
        }
 
    }
}