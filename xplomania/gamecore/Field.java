import java.awt.*;

public class Field{
	public int blockSize;
	public int posX;
    public int posY;
	public int type; 	//kind of field --> 	0 = empty
						//						1 = Hardblock
						//						2 = Softblock without goody
						//						3 = Softblock with healthgoody
						//						4 = Softblock with firepowergoody
						//						5 = Softblock with incBombgoody
						//						6 = healthgoody
						//						7 = firepowergoody
						//						8 = incBombgoody
	Image hardBlock;
	Image softBlock;
	Image mediPack;
	Image firePower;
	Image incBomb;
	GameController gameContr;
	
	public int xManOnMe;
			 
	public Field(int blockSize,int posX, int posY, int xManOnMe,
										 int type,GameController gameContr){	
		this.posX = posX;
		this.posY = posY;
		this.type = type;
		this.blockSize =blockSize;
		this.gameContr = gameContr;
		hardBlock = Toolkit.getDefaultToolkit().getImage
										("F:/projects/javaprojekts/xplomania/src/Block/hardBlock.jpg");
		softBlock = Toolkit.getDefaultToolkit().getImage
										("F:/projects/javaprojekts/xplomania/src/Block/softBlock.png");
		mediPack = Toolkit.getDefaultToolkit().getImage
										("F:/projects/javaprojekts/xplomania/src/Goodies/Medipack.png");
		firePower = Toolkit.getDefaultToolkit().getImage
										("F:/projects/javaprojekts/xplomania/src/Goodies/Firepower.png");
		incBomb = Toolkit.getDefaultToolkit().getImage
										("F:/projects/javaprojekts/xplomania/src/Goodies/incBomb.png");
	}
	
	public void showField(Graphics g){
	switch (type) {
			case 0:				
				//do nothing
				g.setColor(new Color(107,107,107));
				g.fillRect(posX,posY,blockSize,blockSize);
				break;
			
			case 1:
				g.setColor(new Color(36,36,36));
				//g.fillRect(posX,posY,blockSize,blockSize);
				g.drawImage(hardBlock,posX,posY,blockSize,blockSize,gameContr);
				break;
				
			case 2:
				g.setColor(new Color(255,114,86));				
				g.drawImage(softBlock,posX,posY,blockSize,blockSize,gameContr);
				break;
			
			case 3:
				g.setColor(new Color(255,114,86));
				g.drawImage(softBlock,posX,posY,blockSize,blockSize,gameContr);
				break;
				
			case 4:
				g.setColor(new Color(255,114,86));				
				g.drawImage(softBlock,posX,posY,blockSize,blockSize,gameContr);
				break;
			
			case 5:
				g.setColor(new Color(255,114,86));
				g.drawImage(softBlock,posX,posY,blockSize,blockSize,gameContr);
				break;
				
			case 6:
				g.drawImage(mediPack,posX,posY,blockSize,blockSize,gameContr);
				break;
			
			case 7:
				g.drawImage(firePower,posX,posY,blockSize,blockSize,gameContr);
				break;
				
			case 8:				
				g.drawImage(incBomb,posX,posY,blockSize,blockSize,gameContr);
				break;
			

		}	
	}
	
}