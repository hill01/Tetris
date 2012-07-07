import javax.swing.JFrame;


public class Tetris {
	
	public static void main(String[] args){
		JFrame frame = new JFrame("Tetris");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		TetrisPane tetrisPane = new TetrisPane();
		frame.setContentPane(tetrisPane);
		frame.pack();
		frame.requestFocusInWindow();
		frame.setVisible(true);
	}
}
