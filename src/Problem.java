import java.util.Random;
public class Problem {
    //instance variable
    private int op1;
    private int op2;
    private int answer;
    private char operator;
    
 public Problem(int answer){
  this.answer = answer;
 }
 //constructor to initilaize
public Problem(char operator){
    op1 = (int)(1+Math.random()*20);
    op2 = (int)(1+Math.random()*20);
    this.operator=operator;
}
 public int getAns(){
        return answer;
 }
 public void setAns(int theAnswer){
        answer=theAnswer;
 }
public int getAnswer(){
    switch(operator){
        case '+':
       answer = op1+op2;
        break;
        case '-':
         answer = op1-op2;
        
        break;
        case '*':
        answer = op1*op2;
        break;
        case '/':
        answer = op1/op2;
        break;
        default:
            break;
    }
   
 
    return answer;
}

public String printAns(){
     if (getAns() == getAnswer())
    return "You are correct!";
     
     return  "Your answer is wrong."; 
    
}

public String toString(){
    return "Question "+ op1 + operator + op2 + " = " +  "??" ;
}
}
