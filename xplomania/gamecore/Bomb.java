import java.awt.*;
import java.awt.event.*;
import java.lang.*;

public class Bomb extends Thread{
	Graphics g;
	int firepower;
	int x;	
	int y;
	int blockSize;
	int xBlock, yBlock;
	
	boolean detonated;
	boolean goRight= true, goLeft= true, goDown= true, goUp = true; //für die Ausbreitung der Explosion
	
	GameMap gameMap;//zum zeichnen der Mapp nach Explosion
	GameController gameContr;
	XMan xMan;
	
	Image[] bombFrames = new Image[10]; //phi: 10 Frames für Bombe
	Image[] xplosionFrames = new Image[5]; //phi: 5 Frame für Explosion
	
	SoundEffects sound = new SoundEffects("F:/projects/javaprojekts/xplomania/src/Sounds/explosion.wav");
	
	public Bomb(int firepower,int x, int y,int blockSize,boolean detonated,
				Graphics g,GameMap gameMap, GameController gameContr,XMan xMan){
	
		this.firepower = firepower;
		this.x = x;
		this.y = y;
		this.detonated = detonated;
		this.gameMap = gameMap;
		this.gameContr = gameContr; // für Info, welcher XMan getroffen
		this.g = g;
		this.xMan = xMan; //phi
		this.blockSize = blockSize;
		
		//Zentrum der Detonation
		xBlock = (x-1)/blockSize;
		yBlock = (y-1)/blockSize;
		
		
		//init BombFrames ( Bilder ! )
		System.out.println("init bombFrames");
		for ( int i = 0; i<bombFrames.length; i++ ) {
			bombFrames[i] = Toolkit.getDefaultToolkit().getImage
											("F:/projects/javaprojekts/xplomania/src/gfx/bomb/bomb_000"+i+".png");
		}
		//init detonationFrames
		System.out.println("init xplosionFrames");	
		for ( int i = 0; i<xplosionFrames.length; i++ ) {
			xplosionFrames[i] = Toolkit.getDefaultToolkit().getImage
											("F:/projects/javaprojekts/xplomania/src/gfx/explosion_var1a/myExpl_000"+i+".png");
		}
	}
	
	public void run(){
		if (detonated == false){
			// Bombe im Countdown zeichnen
			for (int i = 0;i<bombFrames.length;i++){
				//System.out.println("BombFrames["+i+"] ( "+bombFrames.toString()+"\n");
				g.setColor(new Color(107,107,107));
				g.drawImage(bombFrames[i], x, y, gameContr );
				try{
					Thread.sleep(300);	
				}
				catch(InterruptedException e) {
					System.err.println("sleep exception");
				}
			}
			//jetzt explodiert die Bombe:
			sound.start();
			
			System.out.println("Xplosion");
			// Explosion in alle 4 Richtungen
			
	
			for ( int i=1; i<=2; i++) {
				//System.out.println("Original: xBlock"+xBlock+" yBlock"+yBlock);
				//Zentrum der Explosion
				detonation(xBlock, yBlock);
				for ( int j=1; j<=i; j++ ) {
					// nach rechts
					//System.out.println("nach Rechts: "+goRight);
					if ( goRight ) { goRight = detonation( xBlock+j, yBlock ); }
					// nach links
					//System.out.println("nach L:      "+goLeft);
					if ( goLeft )  { goLeft  = detonation( xBlock-j, yBlock ); }
					// nach unten
					//System.out.println("nach Unten:  "+goDown);
					if ( goDown )  { goDown  = detonation( xBlock, yBlock+j ); }
					//nach oben
					//System.out.println("nach Oben:   "+goUp);
					if ( goUp )    { goUp    = detonation( xBlock, yBlock-j ); }
				}
				try{
					Thread.sleep(100);	
				}
				catch(InterruptedException e) {
					System.err.println("sleep exception");
				}
			}

			// Abchecken wer/was getroffen wurde
			gameContr.detonation(firepower,xBlock,yBlock);
			System.out.println("bomb.detonation@"+xBlock+":"+yBlock);
			
			// Hintergrund neu zeichnen
			gameMap.showMap(g);
			
			xMan.decCurrentNumberOfBomb();
			detonated = true;
			//sound = null;
			}
	}  //end of Thread
	

	public boolean detonation(int xBlk, int yBlk){
		boolean check = checkField(xBlk,yBlk);
		if ( check ) {
			//System.out.println("++>in xplosion: x="+xBlk+" y="+yBlk+" check="+check);
			for ( int i = 0; i<xplosionFrames.length; i++ ) {
				g.setColor(new Color(107,107,107));
				g.drawImage(xplosionFrames[i], xBlk*blockSize, yBlk*blockSize, gameContr );
				try{
					Thread.sleep(10);	
				}
				catch(InterruptedException e) {
					System.err.println("sleep exception");
				}
			}
			return true;
		}
		else { System.out.println("check="+check); return false;  }
	} // end of method detonation
	
	public boolean checkField(int xxx, int yyy){
		boolean check = false;
		// ArrayIndexOutOfBounds Exception vermeiden
		if ( xxx < 0 ) { xxx = 0; }
		if ( yyy < 0 ) { yyy = 0; }
		
		// Ausbreitung regeln
		int type = gameMap.fieldArr[xxx][yyy].type;
		System.out.println("Field Typ: "+type);
		switch ( type ) {
			case 0: check = true;	break; //leeres feld
			case 1: check = false; 	break; 	//hardBlock
			case 2: check = false; 	break;	//softBlock mit Goody
			case 3: check = false; 	break;	//softBlock ohne Goody
			case 4: check = false; 	break; 	//softBlock ohne Goody
			case 5: check = false; 	break;	//softBlock ohne Goody
			case 6: check = true;  	break;	//Goody
			case 7: check = true; 	break; 	//Goody
			case 8: check = true;  	break;	//Goody
		}
		return check;
	} // end of Method "checkField"
	
}