import java.awt.Dimension;

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
	}
	
	public void setLevel(int l){
		level = l;
	}
	
	public void setLinesCleared(int lc){
		linesCleared = lc;
	}
}
