import java.util.Scanner;
public class TestProblem {

    
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
         int count = 0;
        Problem p = new Problem('+');
    	System.out.println(p);
    	int answer = input.nextInt();
        p.setAns(answer);
        System.out.println(p.printAns());
        System.out.println(p.getAnswer());
     
        
        Problem p2 = new Problem('+');
    	System.out.println(p2);
    	answer = input.nextInt();
        p2.setAns(answer);
        System.out.println(p2.printAns());
        System.out.println(p2.getAnswer());
        
        
       

    }
    
}

