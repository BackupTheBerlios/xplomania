import java.awt.*;
import java.lang.*;
import java.io.*;

public class InputOutput{
	public String path = "F:/projects/javaprojekts/xplomania/src/Profiles/UserProfiles.ini";	
	GameController gameContr;
	
	public InputOutput(GameController gameContr){
		this.gameContr = gameContr;
	
	}
	
	public synchronized void handleInput(XMan xMan, int keyEvent){
		if (keyEvent == xMan.rightKey){
			xMan.isMoving = true;
     		xMan.direction = 3;
		}
		if (keyEvent == xMan.leftKey){
			xMan.isMoving = true;
     		xMan.direction = 2;
		}
		if (keyEvent == xMan.upKey){
			xMan.isMoving = true;
     		xMan.direction = 0;
		}
		if (keyEvent == xMan.downKey){
			xMan.isMoving = true;
     		xMan.direction = 1;
		}
		if (keyEvent == xMan.bombKey){
			gameContr.createBomb(xMan);
		}
	}
	
	//nachfolgende Funktionen steuern das Ein und Auslesen der UserProfile
	public String readInIFile(String path,String header, String position){
	BufferedReader inFile;
	String back = "";
	try{
   			String c;
	   		String line;
	   		boolean headerFound = false;
	   		int k = 0;
	   		inFile = new BufferedReader(new FileReader(path));
	   		while((line = inFile.readLine()) != null){
	   			if (line.equals("[" + header + "]")){
	   				headerFound = true;	
	   			}
	   			if (headerFound == true){
	   				if (line.length() >= position.length()){
		   				if (line.substring(0,position.length()).equals(position)){
		   					back = line.substring(position.length()+1,line.length());
		   					headerFound = false; 	
		   				}
	   				}	
	   			}
	   			
	   		}
	   		inFile.close();
	   		return back;
	    }
	    catch (IOException e){
	    	System.err.println("Error in file" + path
	    										 + ": " + e.toString());
	 	}
	return back;		
	}
	
	public int findPosInInIFile(String path,String header, String position){
	BufferedReader inFile;
	int back = -1;
	try{
   			String c;
	   		String line;
	   		boolean headerFound = false;
	   		boolean posFound = false;
	   		int k = 1;
	   		inFile = new BufferedReader(new FileReader(path));
	   		while(((line = inFile.readLine()) != null) && (posFound == false)){
	   			if (line.equals("[" + header + "]")){
	   				headerFound = true;	
	   			}
	   			if (headerFound == true){
	   				if (line.length() >= position.length()){
		   				if (line.substring(0,position.length()).equals(position)){
		   					posFound = true;
		   					headerFound = false; 	
		   				}
	   				}	
	   			}
	   		k = k + 1;	
	   		}
	   		if (posFound == true){
	   			back = k;
	   		}
	   		
	   		inFile.close();
	   		return back;
	    }
	    catch (IOException e){
	    	System.err.println("Error in file" + path
	    										 + ": " + e.toString());
	 	}
	return back;		
	}
	
	public boolean writeInIFile(String path,String header
													,String position,String text){
		boolean back = false;
		BufferedReader inFile;
		PrintWriter outFile;
		int posInFile;
		int k = 1;
		String[] textToSet;
		int strArrLength = 0;
		posInFile = findPosInInIFile(path,header,position);
		if (posInFile != -1){
		//Arraylänge textToSet bestimmen
			try{
				String line;
	   			inFile = new BufferedReader(new FileReader(path));
		   		while((line = inFile.readLine()) != null){
		   			k = k + 1;
		    	}
		    strArrLength = k;
		    inFile.close();}
		    catch (IOException e){
		    	System.err.println("Error in file" + path
		    										 + ": " + e.toString());
		 	}
		 	textToSet = new String[strArrLength];
		 	k = 1;
		 	try{
				String line;
	   			inFile = new BufferedReader(new FileReader(path));
		   		while((line = inFile.readLine()) != null){
		   			if (k == posInFile - 1){
		   				
		   				textToSet[k] = position + "=" + text;
		   			}else{
		   				textToSet[k] = line;
		   			}
		   		k = k + 1;
		    	}
		    inFile.close();
		 	}
		    catch (IOException e){
		    	System.err.println("Error in file" + path
		    										 + ": " + e.toString());
		 	}
		 	try{
		 		outFile = new PrintWriter(new FileWriter(path),true);
		 		for (int i = 0;i<textToSet.length;i++){ 
		 			outFile.println(textToSet[i]);
		 		}
		 		outFile.close();
		 		back = true;
		 	}
		 	catch(IOException e){
		 		System.err.println("File Error: " + e.toString());
			}
		}
		return back;						
	} 
	
	
}