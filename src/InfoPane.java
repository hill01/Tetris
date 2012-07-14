import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;


@SuppressWarnings("serial")
public class InfoPane extends JPanel{
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
	
	public void paintComponent(Graphics g){
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);
		g.setColor(Color.BLACK);
		g.drawString("Points: " + score, 10, 50);
		g.drawString("Lines Cleared: " + linesCleared, 10, 100);
		g.drawString("Level: " + level, 10, 150);
	}
}
