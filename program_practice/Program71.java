public class Program71 {
    public static void main(String[] args) {
        
        int year=1200;

        if(year%4==0){
            System.out.println("leap year");
        }
        else if(year%400==0 || year%100==0){
            System.out.println("leap year");
        }
        else{
            System.out.println("not leap year");
        }
    }
}
