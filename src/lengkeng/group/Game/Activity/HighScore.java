package lengkeng.group.Game.Activity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.anddev.andengine.ui.activity.BaseGameActivity;

import android.annotation.SuppressLint;

public class HighScore {
	public static int[][] highScore = new int[5][5];
	// Khoi tao file Score.txt sau do doc vao mang highScore
	@SuppressWarnings("static-access")
	@SuppressLint("WorldWriteableFiles")
	public void creatScore(BaseGameActivity context){
		File file = context.getBaseContext().getFileStreamPath("Score.txt");
		String eol = System.getProperty("line.separator");
        BufferedWriter writer = null;
        String Text = new String("");
        for (int i=1; i<=16; i++)
        	Text = Text +"0" + eol; 
        if (!file.exists())
        try { 
        	writer = new BufferedWriter(new OutputStreamWriter(context.openFileOutput("Score.txt", context.MODE_WORLD_WRITEABLE)));           
            writer.write(Text);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
          if (writer != null) {
          try {
            writer.close();
          } catch (IOException e) {
            e.printStackTrace();
          }
          }
        }        
       readScore(context);  
	}
	// Doc vao mang highScore
	public void readScore(BaseGameActivity context) {
	    try { 
            FileInputStream fIn = context.openFileInput("Score.txt");
            InputStreamReader inputreader = new InputStreamReader(fIn);
            BufferedReader buffreader = new BufferedReader(inputreader);             
            for (int i=1; i<5; i++)
            	for(int j=1; j<5; j++)
            		highScore[i][j] = Integer.parseInt(buffreader.readLine());
        } catch (IOException ioe) 
          {ioe.printStackTrace();}
	    
	}
	
	// Ghi lai vao file Score.txt tu mang highScore
	@SuppressWarnings("static-access")
	@SuppressLint("WorldWriteableFiles")
	public void writeScore(BaseGameActivity context) {
		String eol = System.getProperty("line.separator");
        BufferedWriter writer = null;
        String Text = new String("");

        for (int i=1; i<5;i++)
			for (int j = 1; j<5; j++){
			Text = Text+Integer.toString(highScore[i][j])+eol;
		}
		 try { 
	        	writer = new BufferedWriter(new OutputStreamWriter(context.openFileOutput("Score.txt", context.MODE_WORLD_WRITEABLE)));	            
	        	writer.write(Text);
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	          if (writer != null) {
	          try {
	            writer.close();
	          } catch (IOException e) {
	            e.printStackTrace();
	          }
	          }
	        }
	}
	
	// So sanh Score dat duoc voi highScore
	public void setScore(int Score, int x, int y, BaseGameActivity context){
		if (Score > highScore[x][y]){
			highScore[x][y] = Score;
			writeScore(context);
		}
	}
	public int getScore(int x, int y){
		return highScore[x][y];
	}

}
