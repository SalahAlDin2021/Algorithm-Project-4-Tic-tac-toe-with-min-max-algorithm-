
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private Button button1;

    @FXML
    private Button button2;

    @FXML
    private Button button3;

    @FXML
    private Button button4;

    @FXML
    private Button button5;

    @FXML
    private Button button6;

    @FXML
    private Button button7;

    @FXML
    private Button button8;

    @FXML
    private Button button9;

    @FXML
    private Text winnerText;
    
    @FXML
    Button st;
    
    @FXML
    Label lbl;
    
    @FXML
    RadioButton x;
    
    @FXML
    RadioButton o;
    
    @FXML
    private ComboBox scenarios;
    
    @FXML
    ToggleGroup xORo;
    
    private static ObservableList<String> items=FXCollections.observableArrayList("Play With Friend","Easy(Rndom Moves)"," Impossible(Min-Max Algorithm)");
    
    private int playerTurn = 0;
    
    private ArrayList<Button> buttons;
    
    private String[] board= new String[9];
    int rand;
    
    
    private int n,i; 
    boolean isRandomMove,start;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    	scenarios.setItems(items);
    	scenarios.getSelectionModel().select(0);
        buttons = new ArrayList<>(Arrays.asList(button1,button2,button3,button4,button5,button6,button7,button8,button9));
        for(i=0; i <9;i++) {
    		board[i]="";
    	}
    }
    @FXML
    void start(ActionEvent event) {
    	st.setVisible(false);
    	for(i=0; i <9;i++) {
    		board[i]="";
    	}
    	scenarios.setVisible(false);
		lbl.setVisible(false);
		x.setVisible(false);
		o.setVisible(false);
    	if(scenarios.getSelectionModel().getSelectedIndex()!=0) {
    		if(o.isSelected()) {
    			int rand = 0;
    			if(scenarios.getSelectionModel().getSelectedIndex()==1)
    			rand = random_ai();
    			else rand = 0;
                buttons.get(rand).setText("X");
                buttons.get(rand).setDisable(true);
                board[rand]="X";
                playerTurn = 1;
                n++;
    		}
    	}
    }
    @FXML
    void b1(ActionEvent event) {
    	click(button1,0);
    	
    }
    @FXML
    void b2(ActionEvent event) {
    	click(button2,1);
    }
    @FXML
    void b3(ActionEvent event) {
    	click(button3,2);
    }
    @FXML
    void b4(ActionEvent event) {
    	click(button4,3);
    }
    @FXML
    void b5(ActionEvent event) {
    	click(button5,4);
    }
    @FXML
    void b6(ActionEvent event) {
    	click(button6,5);
    }
    @FXML
    void b7(ActionEvent event) {
    	click(button7,6);
    }
    @FXML
    void b8(ActionEvent event) {
    	click(button8,7);
    }
    @FXML
    void b9(ActionEvent event) {
    	click(button9,8);
    }
    
    void click(Button button,int i) {
    	if(scenarios.getSelectionModel().getSelectedIndex()==0) {
    	if(playerTurn % 2 == 0){
            button.setText("X");
            button.setDisable(true);
            board[i]="X";
            playerTurn = 1;
            n++;
            
        } else{
            button.setText("O");
            button.setDisable(true);
            board[i]="O";
            playerTurn = 0;
            n++;
        }
    	checkIfGameIsOver(board,false);
    	}else {
    		if(playerTurn % 2 == 0){
                button.setText("X");
                button.setDisable(true);
                board[i]="X";
                playerTurn = 1;
                n++;
                
            } else{
                button.setText("O");
                button.setDisable(true);
                board[i]="O";
                playerTurn = 0;
                n++;
            }
        	int c =checkIfGameIsOver(board,false);
        	if(c==-2){
        	if(n< 9) {
			int nextMove;
			if(scenarios.getSelectionModel().getSelectedIndex()==1)nextMove= random_ai();
			else {
				if(n>0)
				nextMove= bestt();
				else 
				nextMove= random_ai();
			}
    		if(playerTurn % 2 == 0){
                buttons.get(nextMove).setText("X");
                buttons.get(nextMove).setDisable(true);
                board[nextMove]="X";
                playerTurn = 1;
                n++;
                
            } else{
            	System.out.println(nextMove);
                buttons.get(nextMove).setText("O");
                buttons.get(nextMove).setDisable(true);
                board[nextMove]="O";
                playerTurn = 0;
                n++;
            }

        	checkIfGameIsOver(board,false);
        	}}
    	}
    }

    @FXML
    void restartGame(ActionEvent event) {
        st.setVisible(true);
    	scenarios.setVisible(true);
    	lbl.setVisible(true);
    	x.setVisible(true);
    	o.setVisible(true);
    	n=0;
        playerTurn = 0;
    	for(int i = 0 ; i < buttons.size();i++) {
    		buttons.get(i).setDisable(false);
    		buttons.get(i).setText("");
    		board[i]="";
    		
    	}
        winnerText.setText("Tic-Tac-Toe");
    }
    private int random_ai(){
    	int[] random = new int[9-n];
    	int k = 0 ;
    	for(int i=0;i<9;i++) {
    		if(board[i].equals("")) {
    			random[k++]=i;
    		}
    	}
    	int rnd = new Random().nextInt(Math.abs(random.length));
        return random[rnd];
    }
    
    private int bestNextMove(){
    	if(n==0) {
    		return 4;
    	}
    	ArrayList<Integer> list = new ArrayList<>();
    	ArrayList<Integer> val = new ArrayList<>();
    	String[] board=new String[9];
    	for(int i=0;i<9;i++) {
    		board[i]=this.board[i];
    	}
    	int bestmove=-1,maxScore1=Integer.MIN_VALUE,maxScore2=Integer.MAX_VALUE;
    	for(int i=0;i<9;i++) {
    		if(board[i] == "") {
    			if(playerTurn % 2 == 0){
        			board[i]="X";
        			int checkWin=checkIfGameIsOver(board,true);
        			if(checkWin!=-2) {
        				list.add(checkWin);
        				val.add(i);
        			}else {
        			list.add(functionBestNextMove(board, 1, n+1));
        			val.add(i);
        			board[i]="";
        			}
    			}else {
    				board[i]="O";
        			int checkWin=checkIfGameIsOver(board,true);
        			if(checkWin!=-2) {
        				list.add(checkWin);	
        				val.add(i);
        			}else {
        			list.add(functionBestNextMove(board, 0, n+1));
        			val.add(i);
        			board[i]="";
        			}
    			}
    		}
    	}

    	int res =0;
    	if(playerTurn == 0 ) {
    		for(int i = 1 ; i<list.size();i++) {
    			if(list.get(i)>list.get(res)) {
    				res =i;
    			}
    		}
    	}else {
    		for(int i = 1 ; i<list.size();i++) {
    			if(list.get(i)<list.get(res)) {
    				res =i;
    			}
    		}
    	}
    	return val.get(res);
    }
    int bestMove;
    int functionBestNextMove(String[] board,int turn,int n){
    	ArrayList<Integer> list = new ArrayList<>();
    	ArrayList<Integer> val = new ArrayList<>();
    	for(int i=0;i<9;i++) {
    		if(n==9) {
    			list.add(checkIfGameIsOver(board,true));	
				val.add(i);
    		}
    		if(board[i] == "") {
    			if(turn % 2 == 0){
        			board[i]="X";
        			int checkWin=checkIfGameIsOver(board,true);
        			if(checkWin!=-2) {
        				list.add(checkWin);	
        				val.add(i);
        			}else {
        			int k=functionBestNextMove(board, 1, n+1);
        			list.add(k);
        			val.add(i);
        			}
        			board[i]="";
    			}else {
    				board[i]="O";
        			int checkWin=checkIfGameIsOver(board,true);
        			if(checkWin!=-2) {
        				list.add(checkWin);
        				val.add(i);
        			}else {
        			int k=functionBestNextMove(board, 0, n+1);
        			list.add(k);
        			val.add(i);
        			}
        			board[i]="";
    			}
    		}
    	}
    	int res =0;
    	if(turn == 0 ) {
    		for(int i = 1 ; i<list.size();i++) {
    			if(list.get(i)>list.get(res)) {
    				res =i;
    			}
    		}
    	}else {
    		for(int i = 1 ; i<list.size();i++) {
    			if(list.get(i)<list.get(res)) {
    				res =i;
    			}
    		}
    	}
    	return list.get(res);
    }

    public int checkIfGameIsOver(String[] board , boolean minmax){
    	
            //X winner
            if ( 	   (board[0] + board[1] + board[2]).equals("XXX") || (board[3] + board[4] + board[5]).equals("XXX") 
            		|| (board[6] + board[7] + board[8]).equals("XXX") || (board[0] + board[4] + board[8]).equals("XXX")
            		|| (board[2] + board[4] + board[6]).equals("XXX") || (board[0] + board[3] + board[6]).equals("XXX") 
            		|| (board[1] + board[4] + board[7]).equals("XXX") || (board[2] + board[5] + board[8]).equals("XXX") ) {
            	
            	if(!minmax) {winnerText.setText("X won!");
                winnerText.setFill(Paint.valueOf("RED"));
                for(int i = 0 ; i < buttons.size();i++)buttons.get(i).setDisable(true);}return 1;
            }

            //O winner
            if ( 	   (board[0] + board[1] + board[2]).equals("OOO") || (board[3] + board[4] + board[5]).equals("OOO") 
            		|| (board[6] + board[7] + board[8]).equals("OOO") || (board[0] + board[4] + board[8]).equals("OOO")
            		|| (board[2] + board[4] + board[6]).equals("OOO") || (board[0] + board[3] + board[6]).equals("OOO") 
            		|| (board[1] + board[4] + board[7]).equals("OOO") || (board[2] + board[5] + board[8]).equals("OOO") ) {
            	if(!minmax) {winnerText.setText("O won!");
                winnerText.setFill(Paint.valueOf("RED"));
                for(int i = 0 ; i < buttons.size();i++)buttons.get(i).setDisable(true);}return -1;
            }

        	if(n==9) {if(!minmax) {winnerText.setText("Tie!");winnerText.setFill(Paint.valueOf("LIGHTBLUE"));}return 0;}
            return -2;
    }
    
    int bestt(){
    	// AI to make its turn
    	  int bestScore = Integer.MIN_VALUE,bestScore2=Integer.MAX_VALUE;;
    	  int move = 0,kkk=n;
    	  for (int i = 0; i < 9; i++) {
    	      // Is the spot available?
    	      if (board[i] == "") {
    	    	  if(playerTurn==0) {
    	    		  board[i] = "X";n++;
    	    	        int score = minimax(board, 0, 1);
    	    	        board[i] = "";n--;
    	    	        if (score > bestScore) {
    	    	          bestScore = score;
    	    	          move =i;
    	    	      }  
    	    	  }else {
    	    		  board[i] = "O";n++;
    	    	        int score = minimax(board, 0, 0);
    	    	        board[i] = "";n--;
    	    	        if (score < bestScore2) {
    	    	          bestScore2 = score;
    	    	          move =i;
    	    	      }
    	    	  }
    	        
    	    }
    	  }
    	  n=kkk;
        	return move;
    }
    
    int minimax(String[] board,int depth,int isMaximizing) {//return 0;}
    	  int result = checkIfGameIsOver(board,true);
    	  if (result != -2) {
    	    return result;
    	  }

    	  if (isMaximizing==0) {
    	    int bestScore = Integer.MIN_VALUE;
    	    for (int i = 0; i < 9; i++) {
    	        // Is the spot available?
    	        if (board[i] == "") {
    	          board[i] = "X";n++;
    	          int score = minimax(board, depth + 1, 1);
    	          board[i] = "";n--;
    	          bestScore = Math.max(score, bestScore);
    	      }
    	    }
    	    return bestScore;
    	  } else {
    	    int bestScore = Integer.MAX_VALUE;;
    	    for (int i = 0; i < 9; i++) {
    	        // Is the spot available?
    	        if (board[i] == "") {
    	          board[i] = "O";n++;
    	          int score = minimax(board, depth + 1, 0);
    	          board[i] = "";n--;
    	          bestScore = Math.min(score, bestScore);
    	        }
    	    }
    	    return bestScore;
    	  }
    }
    
    
    
    
    
    
}
