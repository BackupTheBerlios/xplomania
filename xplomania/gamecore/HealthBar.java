import java.awt.*;

public class HealthBar{
	
	int posX;
	int posY;
	int size;
	
	public HealthBar(int posX, int posY, int size){
		this.posX = posX;
		this.posY = posY;
		this.size = size;	
	}	
	public void display(Graphics g,int plusminus){//1 = zunahme, 0 = abnahme
		System.out.println("3 Schritt");
		
		if(plusminus == 0){
			g.setColor(Color.magenta);
			g.fillRect(posX,posY,size * 10,15);
			size = size - 1;
			g.setColor(Color.black);
			g.fillRect(posX,posY,size * 10,15);
		}else{
			size = size + 1;
			g.fillRect(posX,posY,size * 10,15);
		 }
	}
	public void setSize(int newSize){
		size = newSize;	
	}
	
}