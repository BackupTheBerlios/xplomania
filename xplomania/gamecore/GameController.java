import java.awt.*;
import java.lang.*;
import java.io.*;
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.event.*;
import java.awt.event.KeyListener.*;

public class GameController extends Frame implements WindowListener, KeyListener{
	String levelPath;
	int blockSize;
	int playerSize;
	int canvSize;
	int arrSize;
	private BufferedReader inFile;
		
	Bomb[] bomben = new Bomb[500];
	int[] keySettings = new int[10]; 
	
	public boolean hard = false;
	boolean begin = true;
	int currentBomb = 0;
	
	public XMan p1;
	public XMan p2;
	public GameMap gameMap;
	public Field[][] fieldArr;
	//public Image backGround;
	
	XManCanvas canvGraphics;
	GameController gameContr;
	XManCanvas xSettings;
	InputOutput ioHandler = new InputOutput(this);
	
	public GameController(String levelPath,int arrSize){
		this.levelPath = levelPath;
		this.arrSize = arrSize;
		setGameSize();
		addKeyListener(this);
		XManCanvas xRound = new XManCanvas();
		XManCanvas settings = new XManCanvas();
		xSettings = settings;
		canvGraphics = xRound;
		setLayout(new BorderLayout());
		setSize(900,600);
		setVisible(true);
		
		xRound.setLocation(28,40);
		xRound.setBackground(new Color(107,107,107));
		xRound.setSize(canvSize,canvSize);
		xSettings.setLocation(700,40);
		xSettings.setBackground(new Color(107,107,107));
		xSettings.setSize(150,544);
		add(xRound);
		add(xSettings);
		startGame();
		repaint();
	}
	public void setGameSize(){
		if (arrSize == 17){
			canvSize = 544;
			blockSize = 32;
			playerSize = 30;	
		}
		if (arrSize == 35){
			canvSize = 560;
			blockSize = 16;
			playerSize = 14;	
		}	
	}
	
	
	public void windowClosing(WindowEvent e) {
	System.exit(0);
	}
		
	public void windowIconified(WindowEvent e) { }
	public void windowOpened(WindowEvent e) { }
	public void windowClosed(WindowEvent e) { }
	public void windowDeiconified(WindowEvent e) { }
	public void windowActivated(WindowEvent e) { }
	public void windowDeactivated(WindowEvent e) { }
	
	public void paint(Graphics g){

	}
	
	public void keyPressed(KeyEvent event)
   {
      if (event.getKeyCode() == 10 ) {
      	if(begin == true){
      		begin = false;
      		gameMap.showMap(canvGraphics.getDraw());
      		p1.healthbar.display(xSettings.getDraw(),1);
      		p2.healthbar.display(xSettings.getDraw(),1);		
      	}
      }
        
   }

   public void keyReleased(KeyEvent event)
   {
   }

   public void keyTyped(KeyEvent event)
   {
   }
   
   public void startGame(){
   			Graphics g = canvGraphics.getDraw();
      		Graphics g2 = xSettings.getDraw();
      		getKeySettings();
      		fieldArr = loadLevel(levelPath);
      		gameMap = new GameMap(fieldArr,this);
      		gameMap.showMap(g);
      		System.out.println(35/12);
      		p1 = new XMan(new Color(107,107,107),g, g2, blockSize+1, blockSize+1,
      									 5,playerSize,blockSize,1, 1, 3, 0,this,gameMap,
      									 keySettings[0],keySettings[1],keySettings[2],
      									 keySettings[3],keySettings[4],ioHandler,1);
      		p2 = new XMan(new Color(107,107,107),g, g2, blockSize+1, 15*blockSize+1,
      									 5,playerSize,blockSize,1, 1, 3, 0,this,gameMap,
      									 keySettings[5],keySettings[6],keySettings[7],
      									 keySettings[8],keySettings[9],ioHandler,2);
      		
      		p1.start();
      		p2.start();
      		gameMap.showMap(g);
   			
   }
   
   public Field[][] loadLevel(String levelPath){
   		//Felder einlesen um Map zu generieren
   		Field[][] fields = new Field[arrSize][arrSize];
   		try{
   			String c;
	   		String line;
	   		int k = 0;
	   		inFile = new BufferedReader(new FileReader(levelPath));
	   		while((line = inFile.readLine()) != null){
	   			for(int i = 0; i<arrSize;i++){
	   				if (Integer.parseInt(String.valueOf(line.charAt(i))) == 2){
	   					
	   					int lucky =(int)(Math.random()*10) + 1;
	   					
	   					switch (lucky) {
							
							case 3:				
								fields[k][i] = new Field(blockSize,k * blockSize,i*blockSize,0,3,this);
								System.out.println("Goody");
								break;
								
							case 4:				
								fields[k][i] = new Field(blockSize,k * blockSize,i*blockSize,0,4,this);
								System.out.println("Goody");
								break;	
	   						
	   						case 5:				
								fields[k][i] = new Field(blockSize,k * blockSize,i*blockSize,0,5,this);
								System.out.println("Goody");
								break;
							
							case 6:				
								fields[k][i] = new Field(blockSize,k * blockSize,i*blockSize,0,4,this);
								System.out.println("Goody");
								break;
								
							case 7:				
								fields[k][i] = new Field(blockSize,k * blockSize,i*blockSize,0,5,this);
								System.out.println("Goody");
								break;
								
							default:
								fields[k][i] = new Field(blockSize,k * blockSize,i*blockSize,0,
	   							Integer.parseInt(String.valueOf(line.charAt(i))),this);
	   					}
	   				
	   				}else{
	   					fields[k][i] = new Field(blockSize,k * blockSize,i*blockSize,0,
	   						Integer.parseInt(String.valueOf(line.charAt(i))),this);
	   				}
	   			}
	   			k = k + 1;	
	   		}
	   		inFile.close();
	    }
	    catch (IOException e){
	    	System.err.println("Error in file" + levelPath
	    										 + ": " + e.toString());
	 	}
	 	System.out.println("Load abgeschlossen");
	 	
	 	return fields;
	 	
   }	
   	
   public void getKeySettings(){
   		keySettings[0] = Integer.parseInt(ioHandler.readInIFile(ioHandler.path,"p1","up"));
   		keySettings[1] = Integer.parseInt(ioHandler.readInIFile(ioHandler.path,"p1","down"));
   		keySettings[2] = Integer.parseInt(ioHandler.readInIFile(ioHandler.path,"p1","left"));
   		keySettings[3] = Integer.parseInt(ioHandler.readInIFile(ioHandler.path,"p1","right"));
   		keySettings[4] = Integer.parseInt(ioHandler.readInIFile(ioHandler.path,"p1","bomb"));
   		keySettings[5] = Integer.parseInt(ioHandler.readInIFile(ioHandler.path,"p2","up"));
   		keySettings[6] = Integer.parseInt(ioHandler.readInIFile(ioHandler.path,"p2","down"));
   		keySettings[7] = Integer.parseInt(ioHandler.readInIFile(ioHandler.path,"p2","left"));
   		keySettings[8] = Integer.parseInt(ioHandler.readInIFile(ioHandler.path,"p2","right"));
   		keySettings[9] = Integer.parseInt(ioHandler.readInIFile(ioHandler.path,"p2","bomb"));
   }
   
   public void detonation(int distanz, int xExplosion, int yExplosion){
		// phils new implementation

		int xBlock = (p1.posX-1)/blockSize;
		int yBlock = (p1.posY-1)/blockSize;
		System.out.println("Xmen1--> posX:"+p1.posX+" posY:"+ p1.posY);
		System.out.println("gamecontroller.detonation@"+xBlock+":"+yBlock);
		// Ist die Explosion im gleichen Feld wie der xMan?
		if ( ( xBlock == xExplosion ) && ( yBlock == yExplosion ) ) {
			System.out.println("HIT xmen 1");
			p1.playSoundHit();
			p1.decHealth();
		}
		
		/*for (int i = 0;i<firePower;i++){
	   		if (((p1.posX-1 == (x-1 + i*blockSize)) && (p1.posY-1 == (y-1))) ||
	   			 ((p1.posX-1 == x-1 - i*blockSize) && (p1.posY-1 == y-1)) ||
	   			  ((p1.posX-1 == x-1) && (p1.posY-1 == y-1 + i*blockSize)) ||
	   			   ((p1.posX-1 == x-1) && (p1.posY-1 == y-1 - i*blockSize))) {
	   					p1.playSoundHit();
	   					p1.decHealth();
	   					if (p1.getHealth() == 0){
	   						System.exit(1);
	   					}
	   		}
		}
		for (int i = 0;i<firePower;i++){
	   		if (((p2.posX-1 == (x-1 + i*blockSize)) && (p2.posY-1 == (y-1))) ||
	   			 ((p2.posX-1 == x-1 - i*blockSize) && (p2.posY-1 == y-1)) ||
	   			  ((p2.posX-1 == x-1) && (p2.posY-1 == y-1 + i*blockSize)) ||
	   			   ((p2.posX-1 == x-1) && (p2.posY-1 == y-1 - i*blockSize))) {
	   					p2.playSoundHit();
	   					p2.decHealth();
	   					if (p2.getHealth() == 0){
	   						System.exit(1);
	   					}
	   		}
		}*/	
   }
   public void createBomb(XMan xMan){
   		if (xMan.currentNumberOfBomb < xMan.maxNumberOfBomb){
        	xMan.incCurrentNumberOfBomb();
        	bomben[currentBomb]  = new Bomb(xMan.getFirePower(),xMan.getPosX()
        					,xMan.getPosY(),blockSize,false,canvGraphics.getDraw()
        													,gameMap,this,xMan);
        	bomben[currentBomb].start();
        	currentBomb = currentBomb + 1;
   		}
  	}
  
   public boolean moveAllowed(int x,int y,int direction){
   		boolean back = false;
   		int xTest;
   		int yTest;
   		
   		switch (direction){
					
					case 0://up
						xTest = x;
						yTest = y - 8;
						if ((x%blockSize) < 2){
							if (isNoObstacle(gameMap.fieldArr[x/blockSize][y/blockSize]) == true){
								back = true;
							}	
						}
						break;
						
					case 1://down
						xTest = x;
						yTest = y + 8;
						if ((x%blockSize) < 2){
							if (isNoObstacle(gameMap.fieldArr[x/blockSize][(y/blockSize)+1]) == true){
								back = true;
							}
						}
						break;
						
					case 2://left//
						xTest = x - 8;
						yTest = y;
						if ((y%blockSize) < 2 ){
							if (isNoObstacle(gameMap.fieldArr[x/blockSize][y/blockSize]) == true){
								back = true;
							}
						}
						break;
						
					case 3://right
						xTest = x + 8;
						yTest = y;
						if ((y%blockSize) < 2 ){
							if (isNoObstacle(gameMap.fieldArr[(x/blockSize)+1][y/blockSize]) == true){
								back = true;
							}
						}
						break;
				}
   		
   		return back;
   }
   
   


	

	public boolean isNoObstacle(Field field){
		boolean back = false;
		switch(field.type){
			case 0:
				back = true;
				break;
				
			case 6:
				back = true;
				break;
				
			case 7:
				back = true;
				break;
				
			case 8:
				back = true;	
				break;
		
		}
		return back;
	}

}

