public class Program16 {
    public static void main(String[] args) {
        
        String s="program";
        for(int i=0;i<s.length();i++){
            //check if already counted
            boolean visited=false;
            for(int k=0;k<i;k++){
                if(s.charAt(i)==s.charAt(k)){
                    visited=true;
                    break;
                }
            }
            if(visited) continue;

            int count=0;
            for(int j=0;j<s.length();j++){
                if(s.charAt(i)==s.charAt(j)){
                    count++;
                }
            }
            System.out.println(s.charAt(i)+"->"+count);
        }
    }
}
