public class Program103 {
    public static void main(String[]args){

        String s="abcabcbb";
        int max=0;

        for(int i=0;i<s.length();i++){

            String temp="";

            for(int j=i;j<s.length();j++){
                char ch=s.charAt(j);

                if(temp.contains(ch+" "))
                    break;

                temp+=ch;
                max=Math.max(max,temp.length());
            }
        }
        System.out.println(max);
    }
}
