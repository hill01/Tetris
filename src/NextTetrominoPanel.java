import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.List;

import javax.swing.JPanel;

//this panel displays the next tetromino
@SuppressWarnings("serial")
public class NextTetrominoPanel extends JPanel{
	private Tetromino nextTetromino;
	private int blockSize = 20;
	private int width;
	private int height;
	
	public NextTetrominoPanel(){
		super();
		width = blockSize * 8;
		height = blockSize * 4;
		setPreferredSize(new Dimension(width, height));
	}
	
	public void setNextTetromino(Tetromino t){
		nextTetromino = t;
		repaint();
	}
	
	public void paintComponent(Graphics g){
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, width, height);
		g.setFont(new Font("Monospaced", Font.BOLD, 14));
		g.setColor(Color.GREEN);
		g.drawString("Next:", 10, 25);
		drawNextTetromino(nextTetromino.getCoordinates(), nextTetromino.getColors(), g);
	}
	
	private void drawNextTetromino(List<Integer> coords, Color[] colors, Graphics g){
		for(Integer coord : coords){
			int x = (coord / 100 - 1) * blockSize; 
			int y = (coord % 100 + 2) * blockSize;
			
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
	}
}
