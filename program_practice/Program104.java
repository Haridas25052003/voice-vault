public class Program104 {
    public static void main(String[]args){

        String s="aaabbbccdd";
        String result="";
        int count=1;

        for(int i=0;i<s.length();i++){
            if(i<s.length()-1 && s.charAt(i)==s.charAt(i+1)){
                count++;
            }
            else{
                result+=s.charAt(i)+""+count;
                count=1;
            }
        }
        System.out.println(result);
    }
}
