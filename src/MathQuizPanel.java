
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javax.swing.JOptionPane;


public class MathQuizPanel extends Application  implements Runnable{
   Integer STARTTIME = 24;
   Timeline timeline;
   IntegerProperty timeSeconds = new SimpleIntegerProperty(STARTTIME);
   RadioButton rbAdd = new RadioButton("Addition");
   RadioButton rbSub = new RadioButton("Subtraction");
   RadioButton rbMulti = new RadioButton ("Multiplication");
   RadioButton rbDiv = new RadioButton("Division");
   Button btnStart = new Button("Start");
   Button btnQuestion = new Button("Get Question");
   Button btnSubmit = new Button("Submit");
   Button btnExit = new Button("Exit");
   TextField tfProblem = new TextField();
   Text count = new Text();
   Label countD = new Label("Count Down");
   Label lbAns = new Label("Enter Answer");
   TextField tfAns = new TextField();
   TextArea taResult = new TextArea();
   
     double ans ;
    int counts = 30,correct=0,wrong=0,question;
   
   Problem p;
    public void start(Stage primaryStage) {
        //functions of the system
        GridPane p = new GridPane();
        p.setHgap(5);
        p.setVgap(5);
        HBox button = new HBox(30);
        button.getChildren().addAll(rbAdd,rbSub,rbMulti,rbDiv);
        //1st row
        p.add(button,1,0);
        //2st row
        HBox ps = new HBox(30);
        ps.getChildren().addAll(btnStart,btnQuestion,countD,count,tfProblem);
        //2ndrow
        p.add(ps,1,1);
        btnQuestion.setVisible(false);
        
        tfProblem.setEditable(false);
        
        //timer for the quiz
        //count.textProperty().bind(timeSeconds.asString());
        count.setVisible(false);
        
        //3rd row
        //label answer
       HBox labelHb = new HBox(30);
       labelHb.setAlignment(Pos.CENTER);
       labelHb.getChildren().addAll(lbAns);
       p.add(labelHb,1,2);
       
       //textField
        //4th row
         p.add(tfAns,1,3);
         
        //button submit
        HBox buttonHb1 = new HBox(10);
        buttonHb1.setAlignment(Pos.CENTER);
        buttonHb1.getChildren().addAll(btnSubmit);
       //5th row
       p.add(buttonHb1, 1, 4);
        //result
        taResult.setPrefSize(500, 450);
        ScrollPane sp = new ScrollPane(taResult);
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        taResult.setEditable(true);
         VBox txtAreaVbox = new VBox(5);
         txtAreaVbox.setPadding(new Insets(5, 5, 5, 5));
         txtAreaVbox.getChildren().addAll(sp);
         //6th row
         p.add(txtAreaVbox,1,5);
         
         
        //button exit      
        HBox buttonHb2 = new HBox(10);
		buttonHb2.setAlignment(Pos.CENTER);
		buttonHb2.getChildren().addAll(btnExit);
        //7th row
        p.add(buttonHb2,1,6);
                
        
        
        //Toggle
        ToggleGroup group = new ToggleGroup();
        rbAdd.setToggleGroup(group);
        rbSub.setToggleGroup(group);
        rbMulti.setToggleGroup(group);
        rbDiv.setToggleGroup(group);
        
        VBox vbox = new VBox(30);
        vbox.setPadding(new Insets(5, 5, 5, 5));
        vbox.getChildren().addAll(p);
        
        /*
        	//create and register the handler
    	btnStart.setOnAction(new EventHandler<ActionEvent>(){
    		public void handle(ActionEvent e){
    		 	   if (timeline != null) {
                              
                    timeline.stop();                    
                }
                timeSeconds.set(STARTTIME);
                timeline = new Timeline();
                timeline.getKeyFrames().add(
                        new KeyFrame(Duration.seconds(STARTTIME+1),
                        new KeyValue(timeSeconds, 0)));
                timeline.playFromStart();
                count.setVisible(true);
                btnQuestion.setVisible(true);
                btnExit.setVisible(false);
                timeline.setOnFinished(event ->Pop());
            }
    	});*/
        //-----------------------start button multhithreading  ----------\\
        // Create the Event-Handlers for the Buttons
		btnStart.setOnAction(new EventHandler <ActionEvent>()
		{
            public void handle(ActionEvent event)
            {   
                count.setVisible(true);
                btnQuestion.setVisible(true);
                btnExit.setVisible(false);
            	
                
            }
            
        });

		btnExit.setOnAction(new EventHandler <ActionEvent>()
		{
            public void handle(ActionEvent event)
            {   
               
        
               
            }
        });
       
        //display the question
       btnQuestion.setOnAction(e -> display());
       //exit the system
       btnExit.setOnAction(e -> exit());
       //keyboard event
       btnSubmit.setOnAction(e-> answer());
          tfAns.setOnKeyPressed(
                e-> {
                    if(e.getCode().isDigitKey() ||
                       e.getCode().isKeypadKey()||
                       e.getCode().equals(KeyCode.LEFT)  ||
                       e.getCode().equals(KeyCode.RIGHT) || 
                       e.getCode().equals(KeyCode.ENTER) ||
                       e.getCode().equals(KeyCode.BACK_SPACE) 
                       ) {/*empty*/}
                    else {
                        displayError();
                    }
                }
        );
        Scene scene = new Scene(vbox, 500, 450);
        
        primaryStage.setTitle("Quiz App");
        primaryStage.setScene(scene);
        primaryStage.show();
        
      Thread t = new Thread(this);
        t.start();
    }
     public void Pop() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Time");
        alert.setHeaderText(null);
        alert.setContentText("Time is up, Questions answered = "+ question+ "\nTotal Correct Answer = " + correct);
        alert.show();
        clear();
    }
      public void display(){
          
               if(rbAdd.isSelected()){
                 p = new Problem('+');
                tfProblem.setText(p.toString());
                question++;
               }
               if(rbSub.isSelected()){
                 p = new Problem('-');
                tfProblem.setText(p.toString());
                question++;
               }
                if(rbMulti.isSelected()){
                p = new Problem('*');
                tfProblem.setText(p.toString());
                question++;
               }
                if(rbDiv.isSelected()){
                 p = new Problem('/');
                tfProblem.setText(p.toString());
                question++;
               }
               
    }
      public void answer(){
          /*
          try{
              int answer = Integer.valueOf(tfAns.getText());
                p.setAns(answer);
                taResult.setText(String.valueOf(p.printAns()));
             }catch(NumberFormatException ex){
                displayError();
        }
         */
           //blank input & numeric input
        try{
         
            ans = Integer.valueOf(tfAns.getText());
            if(ans == p.getAnswer()){
                taResult.setText("You Are Correct");
                tfAns.setText("");
                correct++;
            }else{
                taResult.setText("You Are Wrong!\nThe correct answer is " + p.getAnswer());
                tfAns.setText("");
                wrong++;
            }
            
           
             }catch(NumberFormatException ex){
                displayError();
        }
           
      }
 public void displayError(){
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Invalid Data");
    alert.setHeaderText(null);
    alert.setContentText("Enter number only!");					
    alert.show();
    }
      public void clear(){
        tfProblem.setText("");
        tfAns.setText("");
        taResult.setText("");
        btnQuestion.setVisible(false);
        count.setVisible(false);
        btnExit.setVisible(true);
          
    }
      public void exit(){
        System.exit(0);
    }
    
 public void run(){ //2md step countdown
       //30 seconds
       for(int i=0;i<31;i++){
           try{
               Platform.runLater(new Runnable(){
                   @Override
                   public void run(){
                       count.setText(counts + "");
                       counts = counts - 1;
                   }
                   
               });
               
               
               Thread.sleep(1000);//pulse 1 second
           }catch(InterruptedException e){
               e.printStackTrace();
           }
       
   }
    
        JOptionPane.showMessageDialog(null,"Time is up, Questions answered = "+ question+ "\nTotal Correct Answer = " + correct);
        clear();
   }

    
    public static void main(String[] args) {
        launch(args);
    }
    
}
