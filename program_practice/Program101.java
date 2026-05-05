public class Program101 {
    public static void main(String[]args){

        String s="programming in java";
        int vow=0;
        int cons=0;

        for(int i=0;i<s.length();i++){
            char ch=s.charAt(i);
            if(ch=='a' || ch=='e' || ch=='i' || ch=='o' || ch=='u'){
                vow++;
            }
            else{
                cons++;
            }

        }
        System.out.println(vow);
        System.out.println(cons);


    }
}
