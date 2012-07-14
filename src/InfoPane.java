import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;


@SuppressWarnings("serial")
public class InfoPane extends JPanel{
	int blockSize = 20;
	private static int width;
	private static int height;
	private static int score;
	private static int level;
	private static int linesCleared;
	private static Tetromino nextTetromino;
	
	public InfoPane(){
		super();
		width = 200;
		height = 400;
		setPreferredSize(new Dimension(width, height));
	}
	
	public void setScore(int s){
		score = s;
		repaint();
	}
	
	public void setLevel(int l){
		level = l;
		repaint();
	}
	
	public void setLinesCleared(int lc){
		linesCleared = lc;
		repaint();
	}
	
	public void setNextTetromino(Tetromino t){
		nextTetromino = t;
	}
	
	private void drawNextTetromino(Integer coord, Color[] colors, Graphics g){
		int x = coord / 100 * blockSize;
		int y = coord % 100 * blockSize;
		
		//fill in the main color
		g.setColor(colors[0]);
		g.fillRect(x, y, blockSize, blockSize);
		
		//draw highlights
		g.setColor(colors[1]);
		g.drawLine(x + 1, y + 1, x + blockSize - 1, y + 1);
		g.drawLine(x + 2, y + 2, x + blockSize - 2, y + 2);
		g.drawLine(x + 1, y + 1, x + 1, y + blockSize - 1);
		g.drawLine(x + 2, y + 2, x + 2, y + blockSize - 2);
		
		//draw shading
		g.setColor(colors[2]);
		g.drawLine(x + 3, y + blockSize - 2, x + blockSize - 1, y + blockSize - 2);
		g.drawLine(x + 2, y + blockSize - 1, x + blockSize - 1, y + blockSize - 1);
		g.drawLine(x + blockSize - 2, y + 3, x + blockSize - 2, y + blockSize - 1);
		g.drawLine(x + blockSize - 1, y + 2, x + blockSize - 1, y + blockSize - 1);
		
		//draw outline
		g.setColor(new Color(0, 0, 51));
		g.drawRect(x, y, blockSize, blockSize);
	}
	
	public void paintComponent(Graphics g){
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);
		g.setColor(Color.BLACK);
		g.drawString("Next:" , 10, 10);
		g.drawString("Points: " + score, 10, 150);
		g.drawString("Lines Cleared: " + linesCleared, 10, 200);
		g.drawString("Level: " + level, 10, 250);
		for(Integer block : nextTetromino.getBlockPositions()){
			drawNextTetromino(block, nextTetromino.getColors(), g);
		}
	}
}
