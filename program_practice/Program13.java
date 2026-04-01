public class Program13 {
    public static void main(String[] args) {
        
        int vowels=0;
        int consonants=0;
        String s="java programming";

        for(int i=0;i<s.length();i++){
            char ch=s.charAt(i);
            if(ch=='a' || ch=='e' || ch== 'i' || ch=='o' || ch=='u'){
                vowels++;
            }
            else if(ch >= 'a' && ch <= 'z'){
                consonants++;
            }
        }
        System.out.println(vowels);
        System.out.println(consonants);
    }
}
