import java.awt.BorderLayout;

import javax.swing.JFrame;


public class Tetris {
	
	public static void main(String[] args){
		JFrame frame = new JFrame("Tetris");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		InfoPane infoPane = new InfoPane();
		TetrisPane tetrisPane = new TetrisPane(infoPane);
		frame.setLayout(new BorderLayout());
		frame.add(tetrisPane, BorderLayout.LINE_START);
		frame.add(infoPane, BorderLayout.LINE_END);
		frame.pack();
		frame.requestFocusInWindow();
		frame.setVisible(true);
	}
}
