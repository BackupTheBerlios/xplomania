import java.awt.*;
import java.lang.*;
import java.io.*;
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.event.*;
import java.awt.event.KeyListener.*;

public class XMan extends Thread implements KeyListener{
	Color color;
	Graphics g;
	Graphics g2;
	int blockSize;
	int playerSize;
	int posX;
	int posY;
	int health;
	int firePower;
	int maxNumberOfBomb;
	int currentNumberOfBomb;
	int direction; //0 = up, 1 = down, 2 = left, 3 = right
	int currentBomb;
	
	private Image[] goUp = new Image[4];
	private Image[] goDown = new Image[4];
	private Image[] goLeft = new Image[4];
	private Image[] goRight = new Image[4];
	
	
	boolean running = true;
	boolean isMoving = false;
	
	HealthBar healthbar;
	
	public SoundEffects hit = new SoundEffects("F:/projects/javaprojekts/xplomania/src/Sounds/schlag.wav");
	public SoundEffects goody = new SoundEffects("F:/projects/javaprojekts/xplomania/src/Sounds/juhu.wav");
	GameController gameContr;
	GameMap gameMap;
	
	XManCanvas xRound;
	InputOutput ioHandler;
	
	public int rightKey;
	public int leftKey;
	public int upKey;
	public int downKey;
	public int bombKey;
	
	public int myNumber;
	
	boolean keyPressed = false;
	
	public XMan(Color color, Graphics g,Graphics g2, int posX, int posY, int health,
								int playerSize,int blockSize, int firePower, int maxNumberOfBomb,
									int direction, int currentNumberOfBomb,
										GameController gameContr,GameMap gameMap,
										int upKey,int downKey,int leftKey, int rightKey,
										int bombKey,InputOutput ioHandler,int myNumber){
		gameContr.addKeyListener(this);
		this.blockSize = blockSize;
		this.playerSize = playerSize;
		this.color = color;
		this.g = g;
		this.g2 = g2;
		this.posX = posX;
		this.posY = posY;
		this.health = health;
		this.firePower = firePower;
		this.maxNumberOfBomb = maxNumberOfBomb;
		this.direction = direction;
		this.currentNumberOfBomb = currentNumberOfBomb;
		this.gameContr = gameContr;
		this.gameMap = gameMap;
		this.rightKey = rightKey;
		this.leftKey = leftKey;
		this.upKey = upKey;
		this.downKey = downKey;
		this.bombKey = bombKey;
		this.ioHandler = ioHandler;
		
		currentBomb = gameContr.currentBomb;
		healthbar = new HealthBar(30,posY,health);
		healthbar.display(g2,1);
		
		
	}
	public void keyPressed(KeyEvent event){
		if (keyPressed == false){
			keyPressed = true;
			ioHandler.handleInput(this,event.getKeyCode());
		}

	}
	public void keyReleased(KeyEvent event){
		
		isMoving = false;
		keyPressed = false;
   }

   public void keyTyped(KeyEvent event){
   System.out.println(isMoving);
   }
	
	public int getPosX(){
		int back = posX;
		return back;	
	}
	public int getPosY(){
		int back = posY;
		return back;	
	}
	public int getFirePower(){
		int back = firePower;
		return back;
	}
	public void setPosX(int newX){
		int old = posX;
		posX = newX;
		//deletePaint(old,posY);	
	}
	public void setPosY(int newY){
		int old = posY;
		posY = newY;
		//deletePaint(posX,old);
	}
	public void incHealth(){
		if (health <= 4){
		health++;
		healthbar.display(g2,1);
		}	
	}
	public void decHealth(){
		health--;
		healthbar.display(g2,0);	
	}
	public int getHealth(){
		int back;
		back = health;
		return back;	
	}
	public void playSoundHit(){
		hit.playSound();
	}
	public void playSoundGoody(){
		goody.playSound();
	}	
	public void incFirePower(){
		firePower++;	
	}
	public void incMaxNumberOfBomb(){
		maxNumberOfBomb++;	
	}
	public void incCurrentNumberOfBomb(){
		currentNumberOfBomb++;	
	}
	public void decCurrentNumberOfBomb(){
		currentNumberOfBomb--;	
	}
	public boolean getGoody(int fieldType){
		boolean back = false;
		switch (fieldType) {
				
			case 6:
				incHealth();
				back = true;
				playSoundGoody();
				break;
				
			case 7:
				incFirePower();
				back = true;
				playSoundGoody();
				break;
				
			case 8:
				incMaxNumberOfBomb();
				back = true;
				playSoundGoody();
				break;
					
		}
		return back;	
		
	}
	public void deletePaint(int x, int y){
		x = x-1;
		y = y-1;
		g.setColor(color);
		g.fillRect(x,y,blockSize,blockSize);
		
	}
	public void run(){
		int counterUp = 0;
		int counterDown = 0;
		int counterLeft = 0;
		int counterRight = 0;
		int lastCountPos = 0;
		int oldPosX = posX;
		int oldPosY = posY;
		
		
		for (int i = 0;i<4;i++){
			goUp[i] = Toolkit.getDefaultToolkit().getImage
										("F:/projects/javaprojekts/xplomania/src/xMan/xman_c000"+i+".png");
		}
		for (int i = 0;i<4;i++){
			goDown[i] = Toolkit.getDefaultToolkit().getImage
										("F:/projects/javaprojekts/xplomania/src/xMan/xman_a000"+i+".png");
		}
		for (int i = 0;i<4;i++){
			goLeft[i] =  Toolkit.getDefaultToolkit().getImage
										("F:/projects/javaprojekts/xplomania/src/xMan/xman_b000"+i+".png");
		}
		for (int i = 0;i<4;i++){
			goRight[i] =  Toolkit.getDefaultToolkit().getImage
										("F:/projects/javaprojekts/xplomania/src/xMan/xman_d000"+i+".png");
		}
		try{
				Thread.sleep(1000);
			}
			catch(InterruptedException e) {
				System.err.println("sleep exception");
			}
	
		while (running == true){
			deletePaint(oldPosX,oldPosY);
			deletePaint(getPosX(),getPosY());
			switch (direction) {
			
			case 0://up
				
				counterUp = lastCountPos;				
				g.drawImage(goUp[counterUp],posX,posY,
							playerSize,playerSize,gameContr);
				counterUp++;
				if (counterUp == 4){
					counterUp = 0;
				}
				lastCountPos = counterUp;
				break;
				
			case 1://down				
				
				
				counterDown = lastCountPos;				
				g.drawImage(goDown[counterDown],posX,posY,
							playerSize,playerSize,gameContr);
				counterDown++;
				if (counterDown == 4){
					counterDown = 0;
				}
				lastCountPos = counterDown;
				break;
				
			case 2://left				
				
				counterLeft = lastCountPos;				
				g.drawImage(goLeft[counterLeft],posX,posY,
							playerSize,playerSize,gameContr);
				counterLeft++;
				if (counterLeft == 4){
					counterLeft = 0;
				}
				lastCountPos = counterLeft;
				break;
				
			case 3://right				
				
				counterRight = lastCountPos;				
				g.drawImage(goRight[counterRight],posX,posY,
							playerSize,playerSize,gameContr);
				counterRight++;
				if (counterRight == 4){
					counterRight = 0;
				}
				lastCountPos = counterRight;
				break;
			}
			if (isMoving == true){
				int x = getPosX()-1;
      			int y = getPosY()-1;
      			oldPosX = getPosX();
      			oldPosY = getPosY();
				switch (direction){
					
					case 0://up
						if (gameContr.moveAllowed(x, y, direction) == true){
							posY = posY - 8;
						}
						break;
						
					case 1://down
						if (gameContr.moveAllowed(x, y, direction) == true){
							posY = posY + 8;
						}
						break;
						
					case 2://left//
						if (gameContr.moveAllowed(x, y, direction) == true){
							posX = posX - 8;
						}
						break;
						
					case 3://right
						if (gameContr.moveAllowed(x, y, direction) == true){
							posX = (posX + 8);
						}
						break;
				}
				
			}
						
			try{
				Thread.sleep(200);
			}
			catch(InterruptedException e) {
				System.err.println("sleep exception");
			}
		}		
	}
		
}