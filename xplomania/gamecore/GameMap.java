import java.awt.*;
import java.lang.*;
import java.io.*;
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.event.*;
import java.awt.event.KeyListener.*;

public class GameMap{
	public Field[][] fieldArr;
	//Image backGround;
	GameController gameContr;
	public GameMap(Field[][] fieldArr,GameController gameContr){
		this.fieldArr = fieldArr;
		this.gameContr = gameContr;
		//backGround = Toolkit.getDefaultToolkit().getImage
										//("F:/projects/javaprojekts/xplomania/src/Backgrounds/desert.jpg");
	}
	
	public void showMap(Graphics g){
		//g.drawImage(backGround,0,0,544,544,gameContr);
		for (int k = 0;k<fieldArr.length;k++){
			for (int i = 0;i<fieldArr.length;i++){
				fieldArr[i][k].showField(g);
			}		
		}
	}
}


