import java.util.HashSet;

class Program91
{
    public static void main(String[]args){

    String s="programming";
    char ch[]=s.toCharArray();
    HashSet set=new HashSet<>();
    for(int i=0;i<ch.length;i++){
        set.add(ch[i]);
    }
    System.out.println(set);

    StringBuilder sb=new StringBuilder();
    for(Object c : set){
        sb.append(c);
    }
    System.out.println(sb.toString());
    }
}