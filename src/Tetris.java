import java.awt.BorderLayout;

import javax.swing.JFrame;


public class Tetris {

	public static void main(String[] args){
		JFrame frame = new JFrame("Tetris");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		InfoPanel infoPane = new InfoPanel();
		TetrisPanel tetrisPane = new TetrisPanel(infoPane);
		frame.setLayout(new BorderLayout());
		frame.add(tetrisPane, BorderLayout.LINE_START);
		frame.add(infoPane, BorderLayout.LINE_END);
		frame.pack();
		frame.requestFocusInWindow();
		frame.setVisible(true);
	}
}
