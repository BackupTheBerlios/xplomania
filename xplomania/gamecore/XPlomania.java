import java.awt.*;
import java.lang.*;
import java.io.*;
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.event.*;
import java.awt.event.KeyListener.*;

public class XPlomania extends Frame implements KeyListener,
									ItemListener, ActionListener {
	Button play,setKeys1, setKeys2;
	Choice levelChoice, sizeChoice, keyChoice, xManChoice;
	TextArea text;
	GameController controller;
	boolean getSettings = false;
	String key;
	String levelDirectory = "F:/projects/javaprojekts/xplomania/src/";
	InputOutput ioHandler = new InputOutput(controller);
	
	public XPlomania(){
		setTitle("X-PloMANia");
		setLayout(new BorderLayout());
		
		
		Panel north = new Panel();
		Panel south = new Panel();
		Panel center = new Panel();
		Panel left = new Panel();
		Panel right = new Panel();
		
		text = new TextArea(2,40);
		center.add(text);
		text.disable();
		
		play = new Button("Play X-Man");
		north.add(play);
		play.addActionListener(this);
		
		setKeys1 =new Button("set Keysettings");
		setKeys2 =new Button("save Keysettings");
		south.add(setKeys1);
		south.add(setKeys2);
		setKeys1.addActionListener(this);
		setKeys2.addActionListener(this);
		setKeys2.disable();
		levelChoice = new Choice();
		levelChoice.add("Level1");
		levelChoice.add("Level2");
		levelChoice.add("Level3");
		north.add(levelChoice);
		levelChoice.addItemListener(this);
		
		sizeChoice = new Choice();
		sizeChoice.add("17 X 17");
		sizeChoice.add("35 X 35");
		north.add(sizeChoice);
		sizeChoice.addItemListener(this);
		
		xManChoice = new Choice();
		xManChoice.add("p1");
		xManChoice.add("p2");
		south.add(xManChoice);
		xManChoice.addItemListener(this);
		
		keyChoice = new Choice();
		keyChoice.add("up");
		keyChoice.add("down");
		keyChoice.add("left");
		keyChoice.add("right");
		keyChoice.add("bomb");
		south.add(keyChoice);
		keyChoice.addItemListener(this);
		
		add("North",north);
		add("Center",center);
		add("South",south);
		
		text.addKeyListener(this);
	}
	
	public static void main(String [] args){
		XPlomania bomber = new XPlomania();
		bomber.setSize(600,200);
		bomber.setVisible(true);
		
	}
	
	public void windowClosing(WindowEvent e) {
	System.exit(1);
	}	
	public void windowIconified(WindowEvent e) { }
	public void windowOpened(WindowEvent e) { }
	public void windowClosed(WindowEvent e) { }
	public void windowDeiconified(WindowEvent e) { }
	public void windowActivated(WindowEvent e) { }
	public void windowDeactivated(WindowEvent e) { }
	
	public void actionPerformed(ActionEvent actionevent){
		if (actionevent.getSource() == play){
			String levelPath;
			int arrSize;
			setVisible(false);
			System.out.println("Das Spiel wird initialisiert...");
			arrSize = getArrSize();
			levelPath = buildLevelPath(arrSize);
			GameController controller = new GameController(levelPath,arrSize);
		}
		if (actionevent.getSource() == setKeys1){
			System.out.println("test");
			setKeys1.disable();
			setKeys2.enable(true);
			getSettings = true;
			text.enable(true);
			text.requestFocus();
		}
		if (actionevent.getSource() == setKeys2){
			text.setText("");
			text.disable();
			setKeys2.disable();
			setKeys1.enable(true);
			getSettings = false;
			ioHandler.writeInIFile(ioHandler.path,xManChoice.getSelectedItem()
									,keyChoice.getSelectedItem(),key);			
		}	
	}
	public void itemStateChanged(ItemEvent itemevent){
		
	}
	
	
	public void keyPressed(KeyEvent event){
		if (getSettings == true){
			text.setText(" = für " + xManChoice.getSelectedItem() + "."
											 + keyChoice.getSelectedItem());;	
			key = Integer.toString(event.getKeyCode());
			System.out.println(key);
		}
//      if (event.getKeyCode() == 39 ) {//Player1 right
//      	
//      }
//      if (event.getKeyCode() == 38 ) {////Player1 up
//         
//      }
//      if (event.getKeyCode() == 40 ) {//Player1 down
//      	  
//      }
//      if (event.getKeyCode() == 37 ) {//Player1 left
//         	 
//      }
//      if (event.getKeyCode() == 68 ) {//Player2 right
//      	 
//      }
//      if (event.getKeyCode() == 87 ) {//Player2 up
//         
//      }
//      if (event.getKeyCode() == 83 ) {//Player2 down
//      		 
//      }
//      if (event.getKeyCode() == 65 ) {//Player2 left
//         	 
//      }
//      if (event.getKeyCode() == 16 ) {//Set Bomb P2
//      	
//      } 
//      if (event.getKeyCode() == 32 ) {//Set Bomb P1
//      	
//      }
//      if (event.getKeyCode() == 10 ) {//Start
//      	
//	  }  
   }

   public void keyReleased(KeyEvent event)
   {
   	System.out.println(event.getKeyCode());
   }

   public void keyTyped(KeyEvent event)
   {System.out.println(event.getKeyCode());
   }
   public void paint(Graphics g){
	}
   public String buildLevelPath(int arrSize){
	   	String back;
	   	back = levelChoice.getSelectedItem();
	   	back = levelDirectory + arrSize + back + ".txt";
	   	return back;	
   }
   public int getArrSize(){
	   	int back = 0;
	   	if (sizeChoice.getSelectedItem() == "17 X 17"){
	   		back = 17;
	   	}
	   	if (sizeChoice.getSelectedItem() == "35 X 35"){
	   		back = 35;
	   	}
	   	return back;
   }
   
   

}
