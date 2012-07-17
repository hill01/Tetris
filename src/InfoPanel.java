import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

//this panel displays the score, level, etc.
@SuppressWarnings("serial")
public class InfoPanel extends JPanel{
	int blockSize = 20;
	private static int width;
	private static int height;
	private static int score;
	private static int level;
	private static int linesCleared;
	private static NextTetrominoPanel nextPanel;
	
	public InfoPanel(){
		super();
		width = 160;
		height = 400;
		setPreferredSize(new Dimension(width, height));
		setLayout(new BorderLayout());
		nextPanel = new NextTetrominoPanel();
		add(nextPanel, BorderLayout.PAGE_START);
		
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
		nextPanel.setNextTetromino(t);
	}
	
	public void paintComponent(Graphics g){
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, width, height);
		g.setFont(new Font("Monospaced", Font.BOLD, 14));
		g.setColor(Color.GREEN);
		g.drawString("Points: " + score, 10, 150);
		g.drawString("Lines Cleared: " + linesCleared, 10, 200);
		g.drawString("Level: " + level, 10, 250);
	}
}
