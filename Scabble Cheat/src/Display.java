import javafx.application.*;
import java.text.*;
import java.util.*;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.input.*;
import javafx.stage.*;
import javafx.geometry.*;

public class Display extends Application{

	TextField lettersTextField;
	Button button;
	TextArea resultTextArea;
	DatabaseConnection conn;
	CheckBox size;
	CheckBox firstLetter;
	boolean first; //for first letter only results
	boolean sizeTF; //for only exact size results
	
	public static void main(String[] args) {
		launch(args);
	}
	

	@Override
	public void start(Stage stage) throws Exception{
		HBox topRow = new HBox(5);//adds space between objects
		HBox secondRow = new HBox(10);
		topRow.setPadding(new Insets(10,10,10,10));// add space between the two layouts
		secondRow.setPadding(new Insets(10,10,10,10));
		
		VBox layout = new VBox(topRow);
		layout.setPadding(new Insets(10,10,10,10));
		
		lettersTextField = new TextField();
		lettersTextField.setPrefWidth(450);
		lettersTextField.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>(){
			 @Override
		       public void handle(KeyEvent event){
				  if(event.getCode().toString()=="ENTER"){
					  resultTextArea.setText("");
					  if(size.isSelected()){sizeTF=true;}
					  else{sizeTF=false;}
					  if(firstLetter.isSelected()){first=true;}
					  else{first=false;}
					  lookUpWords();
				  }
		       }
		});
		button = new Button("Go");
		button.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent event){
				resultTextArea.setText("");
				if(size.isSelected()){sizeTF=true;}
				else{sizeTF=false;}
				if(firstLetter.isSelected()){first=true;}
		        else{first=false;}
				lookUpWords();
			}
		});
		
		size = new CheckBox("Exact Size");
		firstLetter = new CheckBox("Starts With");
		
		resultTextArea = new TextArea();
		resultTextArea.setEditable(false);
		
		topRow.getChildren().add(lettersTextField);
		topRow.getChildren().add(button);
		secondRow.getChildren().add(firstLetter);
		secondRow.getChildren().add(size);
		layout.getChildren().add(secondRow);
		layout.getChildren().add(resultTextArea);
		
	
		
		Scene scene = new Scene(layout, 600, 500);
		scene.getStylesheets().add("Styles.css");
		stage.setScene(scene);
		stage.setTitle("Scrabble Cheat");
		stage.show();
	}
	
	public void lookUpWords(){
		conn = new DatabaseConnection();
		ArrayList<String> words = conn.LookUpWords(lettersTextField.getText().toLowerCase(), first, sizeTF);
		ArrayList<String> sorted = new ArrayList<String>();
		
////////////////////////////////////////////////////////////////////////////////////sorts array by string length		
		
		int loop = 20;
		while(loop>1){
			for(String x : words){
				if(x.length()==loop){sorted.add(x);}
			}
		loop--;
		}
/////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		for(String word : sorted){
			resultTextArea.appendText(word + "\n");
		}
		
	}
	
}
