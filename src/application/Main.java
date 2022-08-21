package application;
	
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class Main extends Application {
	
	private boolean start = false;
	private boolean clear = false;
	private int max_len = 16;
	private char[] ops1 = {'/', '*'};
	private char[] ops2 = {'+', '-'};
	private char[] nums = {'1', '2', '3', '4', '5', '6', '7', '8', '9', '0'};
	public boolean match1(char input) {
		for (char op : ops1) {
			if (input == op) {
				return true;
			}
		}
		return false;
	}

	public boolean match2(char input) {
		for (char op : ops2) {
			if (input == op) {
				return true;
			}
		}
		return false;
	}	

	public double operate1(double num1, double num2, char op) {
		if (op == '*') {
			return num1 * num2;
		}
		else {
			return num1 / num2;
		}		

	}

	public double operate2(double num1, double num2, char op) {
		if (op == '+') {
			return num1 + num2;
		}
		else {
			return num1 - num2;
		}
	}	
	public boolean is_num(char c) {
		for(char n: nums) {
			if (c == n) {
				return true;
			}
		}
		return false;
	}

	public String update(char[] char_eq, int i) {
		char op = char_eq[i];
		int start = i - 1;
		String string_eq = String.valueOf(char_eq); 
		// traverse backwards untill encountering '+' or '-'
		while (start >= 0) {
			if (match2(char_eq[start])) {
				break;
			} else {
				start = start - 1;
			}
		}
		double num1 = Double.parseDouble(string_eq.substring(start + 1, i));
		int end = i + 1;
		// traverse backwards until encountering '+' or '-' or '*' or '/'
		while (end < char_eq.length) {
			if (match1(char_eq[end]) || match2(char_eq[end])) {
				break;
			} else {
				end = end + 1;
			}
		}
		double num2 = Double.parseDouble(string_eq.substring(i + 1, end));
		double r = operate1(num1, num2, op);
		String new_num = String.valueOf(r);
		String aws = string_eq.substring(0, start + 1) + new_num +  string_eq.substring(end, string_eq.length());
		return aws;
	}

	public String evaluate(String eq) {
		double result = 0;
		double num1 = 0;
		double num2 = 0;
		char op = 'N';
		char [] char_eq = eq.toCharArray(); 
		int i = 0; 
		int start = 0;
		boolean con = true;
		int last_idx = 0;
		while (con) {
			// '+' or '-'
			if (match2(char_eq[i])) {
				last_idx = i;
			} 
			// '*' or '/'
			else if (match1(char_eq[i])) {
				//return a new equation after evaluting the substring including the latest found '*' or '/'
				char_eq = update(char_eq, i).toCharArray();
				i = last_idx;
			} 
			i += 1;
			if (i >= char_eq.length) {
				con = false;
			}
		}
		//new_char_eq only contains '+' or '-' operations
		String tmp = String.valueOf(char_eq);
		for (int j = 0; j < char_eq.length; j++) {
			// '+' or '-'
			if (match2(char_eq[j])) {
				if (op == 'N') {
					num1 = Double.parseDouble(tmp.substring(start, j));
					op = char_eq[j];
					start = j + 1;
				} else {
					num2 = Double.parseDouble(tmp.substring(start, j));
					result = operate2(num1, num2, op);
					op = char_eq[j];
					start = j + 1;
					num1 = result;
				}
			}
		}
		if (op == 'N') {
			result = Double.parseDouble(tmp);
		} else {
			num2 = Double.parseDouble(tmp.substring(start, tmp.length()));
			result = operate2(num1, num2, op);
		}
		String r = String.valueOf(result);
		int r_len = r.length();
		if (r_len == max_len + 1) {
			r = r.substring(0, max_len - 1);
		}
		else if (r_len > max_len) {
			r = r.substring(0, max_len);
		}
		else if (r.charAt(r_len - 1) == '0') {
			r = r.substring(0, r_len - 2);
		} 
		return r;
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			// 1) Set up elements
			VBox root = new VBox();
			HBox h123 = new HBox();
			HBox h456 = new HBox();
			HBox h789 = new HBox();
			HBox hp0m = new HBox();
			HBox hdemul = new HBox();
			
			root.getStyleClass().add("vboxtheme");
			h123.getStyleClass().add("hboxtheme");
			h456.getStyleClass().add("hboxtheme");
			h789.getStyleClass().add("hboxtheme");
			hp0m.getStyleClass().add("hboxtheme");
			hdemul.getStyleClass().add("hboxtheme");			
			
			Button[] digits = new Button[10];
			for (int i=0; i<10; i++) {
				digits[i] = new Button();
				digits[i].setText( Integer.toString((i+1) % 10) );
				digits[i].getStyleClass().add("buttontheme");
			}
			Button plus = new Button();			
			plus.setText("+");
			plus.getStyleClass().add("buttontheme");
			Button minus = new Button();
			minus.setText("-");
			minus.getStyleClass().add("buttontheme");
			Button divide = new Button();
			divide.setText("/");
			divide.getStyleClass().add("buttontheme");
			Button multiply = new Button();
			multiply.setText("*");
			multiply.getStyleClass().add("buttontheme");
			Button equals = new Button();
			equals.setText("=");
			equals.getStyleClass().add("buttontheme");
			
			Button[] opperators = {plus, minus, divide, multiply};

			TextField textField = new TextField();
			textField.getStyleClass().add("textfieldtheme");
			
			// 2) Set up scene graph
			h123.getChildren().add( digits[0] );
			h123.getChildren().add( digits[1] );
			h123.getChildren().add( digits[2] );
			
			h456.getChildren().add( digits[3] );
			h456.getChildren().add( digits[4] );
			h456.getChildren().add( digits[5] );
			
			h789.getChildren().add( digits[6] );
			h789.getChildren().add( digits[7] );
			h789.getChildren().add( digits[8] );
			
			hp0m.getChildren().add( plus );
			hp0m.getChildren().add( digits[9] );
			hp0m.getChildren().add( minus );
			
			hdemul.getChildren().add( divide );
			hdemul.getChildren().add( equals );
			hdemul.getChildren().add( multiply );
			
			root.getChildren().add(h123);
			root.getChildren().add(h456);
			root.getChildren().add(h789);
			root.getChildren().add(hp0m);
			root.getChildren().add(hdemul);
			root.getChildren().add(textField);
			
			// 3) Write event handling

			EventHandler<ActionEvent> optHandler = new EventHandler<ActionEvent>() {
				@Override
				public void handle( ActionEvent ae ) {
					Button btn = (Button)ae.getSource();
					char[] text_char = textField.getText().toCharArray();

					if (is_num(text_char[text_char.length - 1])) {
						String previous1 = textField.getText();
						textField.setText(previous1 + btn.getText());
						clear = false;
						start = false;
					} 
				}
			};

			for (int i = 0; i < opperators.length; i++) {
				opperators[i].setOnAction(optHandler);
			}


			EventHandler<ActionEvent> digitHandler = new EventHandler<ActionEvent>() {
				@Override
				public void handle( ActionEvent ae ) {
					//getSource returns the Object that triggered the event
					//cast it to Button, so we can use the methods in Button
					Button btn = (Button)ae.getSource();
					start = true;
					String previous;
					if (clear) {
						previous = "";
					} else {
						previous = textField.getText();
					}
					textField.setText(previous + btn.getText());
					clear = false;
				}
			};
			
			for (int i=0; i<10; i++) {
				digits[i].setOnAction( digitHandler );
			}

			equals.setOnAction( new EventHandler<ActionEvent>() {
				@Override
				public void handle( ActionEvent ae ) {
					if (start) {
						String eq = textField.getText();
						textField.setText(evaluate(eq));
						clear = true;
					}
				}
			});
			Scene scene = new Scene(root,280,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
