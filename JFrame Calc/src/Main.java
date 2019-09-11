/*
 * Author: Mofiz Uddin
 * refresher on Java
 * Calculator App.
 */
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
/*
 * 
 * GUI
 * 
 */
public class Main {
	final int WIDTH = 320;
	final int HEIGHT = 380;
	JButton[] buttons = new JButton[20];
	JFrame frame;
	JPanel panel;
	JLabel label;
	String text = "";
	double total = 0;
	final String[] values = new String[] {"AC","CE","%","/","7","8","9","*","4","5","6","+","1","2","3","-",".","0","neg","="};
	
	public Main() {
		setFrame();
		setPanel();
		setButtons();
		setLabel();
		setListener();
	}
	public void setFrame() {
		frame = new JFrame("Calculator -By Mofiz");
		frame.setSize(WIDTH+6, HEIGHT);
		frame.setLocationRelativeTo ( null );
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);
		frame.setVisible(true);
		frame.setResizable(false);
	}
	public void setPanel() {
		panel = new JPanel();
		panel.setLayout(null);
		panel.setBounds(0,0,WIDTH, HEIGHT);  
		
		Border blackline = BorderFactory.createLineBorder(Color.white);
		panel.setBorder(blackline);
		frame.add(panel); 
	}
	public void setButtons() {
		int x = 0, y = 100;
		for (int i = 0; i < buttons.length; i++) {
			JButton tempButton = new JButton(values[i]);
			buttons[i] = tempButton;
			buttons[i].setBounds(x,y,80,50);
			if(i==buttons.length-1) {
				buttons[i].setBackground(Color.orange); 
			}else if((i+1)%4==0 || y==120) {
				buttons[i].setBackground(Color.GRAY); 
			}else {
				buttons[i].setBackground(Color.LIGHT_GRAY); 
			}
			panel.add(buttons[i]);
			x += 80;
			if((i+1)%4==0 && i!=0) {
				y += 50;
				x=0;
			}
		}
		buttons[0].setBackground(Color.GRAY);
		buttons[1].setBackground(Color.GRAY);
		buttons[2].setBackground(Color.GRAY);
		buttons[16].setBackground(Color.GRAY);
		buttons[18].setBackground(Color.GRAY);
		panel.setBackground(Color.DARK_GRAY);
	}
	public void setLabel(){
		Border blackline = BorderFactory.createLineBorder(Color.white);
		label = new JLabel();
		label.setLayout(null);
		label.setBounds(0,0,WIDTH, 100); 
		label.setText("0");
		label.setFont (label.getFont ().deriveFont (48.0f));
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setVerticalAlignment(SwingConstants.BOTTOM);
		panel.add(label);
		label.setBorder(blackline);
		label.setForeground(Color.white);
	}
/*
 * 
 * LOGIC
 * 
 */
	public void setListener(){
		for (int i = 0; i < buttons.length; i++) {
			if (i==19){
				buttons[i].addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if(text.length()>1){
							if(text.charAt(text.length()-1)=='/'||text.charAt(text.length()-1)=='*'||text.charAt(text.length()-1)=='-'||text.charAt(text.length()-1)=='+'){
								return;
							}else{
								try {
								calculate();
								label.setText(Double.toString(total));
								}
								catch(Exception e) {
									label.setText("Error");
									total = 0;
									System.out.println(e);
								}
							}
						}
						clearText();
					}
				});
			}else if (i==18){
				buttons[i].addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub
						neg();
						label.setText(getText());
						
					}
				});
			}else if (i==2){
				buttons[i].addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub
						percentage();
						label.setText(getText());
						
					}
				});
			}else if (i==1){
				buttons[i].addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub
						CE();
						label.setText(getText());
					}
				});
			}else if (i==0){
				buttons[i].addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub
						label.setText("0");
						clearText();
					}
				});
			}else{
				buttons[i].addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent click) {
						// TODO Auto-generated method stub
						setText(click.getActionCommand());
						label.setText(getText());
					}
					});
				}
		}
	}
	//sets the button press value to the text (string of equation). If operator with no operand, adds 0 or previous total.
	public void setText(String x){
		if(text.length()==1 && text.equals("0")){
			text = "";
		}
		if (text.length()==0){
			if( x=="/" ||  x=="*" ||  x=="-"  || x=="+" ){
				if (total>0){
					String str = Double.toString(total);
					text = str + x;
				}else if(total<0){
					String str = Double.toString(total);
					text = "(" + str + ")" + x;
				}else{
					text = "0"+ x;
				}
				return;
			}
		}
		if( x=="/" ||  x=="*" ||  x=="-"  || x=="+" ){
			String str = "";
			str += text.charAt(text.length()-1);
			if(str.equals(x)||str.equals("/")||str.equals("*")||str.equals("-")||str.equals("+")){
				return;
			}
			if(text.charAt(text.length()-1)=='.'){
				CE();
			}
		}
		text = text + x;
	}
	//getter method
	public String getText(){
		return text;
	}
	//for AC button
	public void clearText(){
		text = "";
	}
	//for CE button
	public void CE() {
		if (text.length()==0){
			text = Double.toString(total);
			CE();
			return;
		}
		if (text.length()==1){
			text = "0";
			return;
		}
	    text = text.substring(0, text.length() - 1);
	}
	//For negative button. Encase operand with brackets and negative by changing the equation String
	public void neg(){
		int end = text.length()-1;
		String first = "", last = "";
		boolean operatorExists = false;
		if(end==-1){
			if(total!=0){
				if (total<0){
					text = Double.toString(total*-1);
				}else{
					String str = Double.toString(total);
					text = "(-" + str + ")";
				}
			}else{
				text = "0";
			}
			
			return;
		}
		for (int i=end; i>=0; i--){
			if (text.charAt(i)=='/' || text.charAt(i)=='*' || text.charAt(i)=='-' || text.charAt(i)=='+'){
				first = text.substring(0, i+1);
				last = text.substring(i+1, text.length());
				operatorExists = true;
				break;
			}
		}
		if (operatorExists){
			text = first + "(-" + last + ")";
		}else{
			text = "(-"+text+")";
		}
	}
	//for percentage button. locate the last operand, divide by 100 and change the text (String for equation)
	public void percentage(){
		String temp = "";
		int c = text.length()-1;
		if(c==-1){
			if (total!=0){
				String str = Double.toString(total/100.000);
				text = text.substring(0,c+1)+str;
			}
			return;
		}
		while (c>-1){
			if (text.charAt(c)=='/' || text.charAt(c)=='*' || text.charAt(c)=='-' || text.charAt(c)=='+'){
				break;
			}else{
				temp += text.charAt(c);
				c--;
			}
		}
		String reverse="";
	    for(int i=temp.length()-1; i>=0; i--) {
	        reverse = reverse + temp.charAt(i);
	    }
		double val = Double.parseDouble(reverse);
		val = val/100.000000;
		String str = Double.toString(val);
		text = text.substring(0,c+1)+str;
	}
	/*find a suitable array size though splitting text string (equation). Takes the string equation and splits it into an 
	 *array of Strings which holds numbers and operators. uses toPostfix to sort by postfix notation, then solves
	 *using the solve method. 
	 */
	public void calculate(){
		String[] operators = text.split("[\\/\\*\\-\\+]+");
		String[] numbers = text.split("[1234567890]+\\s*");
		if (operators.length==1){
			if(text.length()!=0){
				total = Double.parseDouble(text);
			}
			return;
		}
		System.out.println(text.charAt(text.length()-1));
		
		int brackets = 0;
		int split = 1;
		int dec = 0;
		for(int i=0;i<text.length();i++){
			if(text.charAt(i)=='('){
				brackets=brackets +2;
			}else if(text.charAt(i)=='.'){
				dec++;
			}
		}
		if(text.charAt(0)=='('){
			split = 0;
		}
		String[] infix = new String[numbers.length + operators.length-split-brackets-dec];
		int c = 0, i =0;
		while(c<infix.length || i<text.length()){
			String temp = "";
			if (text.charAt(i)=='('){
				i++;
				while (i<text.length()){
					if(text.charAt(i)==')'){
						break;
					}else{
						temp = temp + text.charAt(i);
						i++;
					}
				}
				i++;
				infix[c] = temp;
			} else if (text.charAt(i)=='/' || text.charAt(i)=='*' || text.charAt(i)=='-' || text.charAt(i)=='+'){
				temp = temp + text.charAt(i);
				i++;
				infix[c] = temp;
			} else{
				while (i<text.length()){
					if (text.charAt(i)=='/' || text.charAt(i)=='*' || text.charAt(i)=='-' || text.charAt(i)=='+'){
						break;
					}else{
						temp = temp + text.charAt(i);
						i++;
					}
				}
				infix[c] = temp;
			}
			c++;
		}
		String[] postfix = toPostfix(infix);
		total = solve(postfix);
	}
	//uses stack to solve postfix equation
	public double solve(String[] postfix){
		Stack<Double> stack = new Stack<Double>();
		for (String a : postfix){
			if (a.equals("/")){
				double x = stack.pop();
				double y = stack.pop();
				stack.push(div(y,x));
			}else if (a.equals("*")){
				double x = stack.pop();
				double y = stack.pop();
				stack.push(mult(x,y));
			}else if (a.equals("+")){
				double x = stack.pop();
				double y = stack.pop();
				stack.push(add(x,y));
			}else if (a.equals("-")){
				double x = stack.pop();
				double y = stack.pop();
				stack.push(sub(y,x));
			}else{
				stack.push(Double.parseDouble(a));
			}
		}
		return stack.pop();
	}
	//Helper methods for basic arithmetic operations
	public double add(double a, double b){
		return a + b;
	}
	public double sub(double a, double b){
		return a - b;
	}
	public double mult(double a, double b){
		return a * b;
	}
	public double div(double a, double b){
		return a / b;
	}
	//turns string of equation from infix to postfix notation.
	public String[] toPostfix(String[] infix){
		Stack<String> stack = new Stack<String>();
		String[] postfix = new String[infix.length];
		int p = 0;
		for(int i=0;i<infix.length;i++){
			boolean pushed = false;
			if(infix[i].equals("/") || infix[i].equals("*") || infix[i].equals("-") || infix[i].equals("+")){
				if (stack.empty()){
					stack.push(infix[i]);
				}else{
					while(pushed==false){
						if (stack.empty()){
							stack.push(infix[i]);
							pushed = true;
						}else {
							int val = getValue(stack.peek());
							if (getValue(infix[i])>=val){
								stack.push(infix[i]);
								pushed = true;
							}else{
								postfix[p] = stack.pop();
								p++;
							}
						}	
					}
				}
			}else{
				postfix[p] = infix[i];
				p++;
			}
		}
		if (!stack.empty()){
			for(int i=p;i<infix.length;i++){
			postfix[i] = stack.pop();
			}
		}
		return postfix;
	}
	//returns precedence value 
	public int getValue(String x){
		if (x.equals("/"))
			return 3;
		else if (x.equals("*"))
			return 2;
		else if (x.equals("-" )|| x.equals("+"))
			return 1;
		else return 0;
	}
	public static void main(String[] args) {
		new Main();
	}
}
