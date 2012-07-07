import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;


@SuppressWarnings("serial")
public class TetrisPane extends JPanel implements ActionListener, KeyListener{
	private static Timer timer;
	private static int blockSize = 20; //determines the size of the panel
	private static int width;
	private static int height;
	private static int speed;
	
	//Integer represents coordinates, the 100 digits represent x coordinates 0-9
	//10 and 1 digits represent y coordinates 0-19
	//e.g. 812 represents the (x, y)-coordinate (8, 12)
	//x = Integer / 100, y = Integer % 100
	//grid contains only locked in blocks, not the active tetromino
	private static Map<Integer, Boolean> grid = new HashMap<Integer, Boolean>();
	
	private static Tetromino currentTetromino;
	
	
	public TetrisPane(){
		super();
		width = blockSize * 10;
		height = blockSize * 20;
		setPreferredSize(new Dimension(width, height));
		setFocusable(true);
		addKeyListener(this);
		speed = 500; //milliseconds, length of one "turn"
		timer = new Timer(speed, this);
		timer.setInitialDelay(speed);
		timer.start();
		
		currentTetromino = nextTetromino();
		
		//the boolean values in the map represent
		//whether or not the space on the grid is occupied
		for(int i = 0; i < 10; i++){
			for(int j = 0; j < 20; j++){
				Integer coords = (i * 100) + j;
				Boolean occupied = false;
				grid.put(coords, occupied);
			}
		}
	}
	
	private void drawBlock(List<Integer> coords, Graphics g){
		for(Integer coord : coords){
			int x = coord / 100 * blockSize;
			int y = coord % 100 * blockSize;
			g.setColor(Color.BLUE);
			g.fillRect(x, y, blockSize, blockSize);
			g.setColor(Color.BLACK);
			g.drawRect(x, y, blockSize, blockSize);
		}
	}
	
	public void paintComponent(Graphics g){
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);
		
		List<Integer> occupiedSpaces = new ArrayList<Integer>();
		for(Integer coord : grid.keySet()){
			if(grid.get(coord) == true){
				occupiedSpaces.add(coord);
			}
		}
		drawBlock(occupiedSpaces, g);
		drawBlock(currentTetromino.getBlockPositions(), g);
	}
	
	private boolean checkEmptyPath(){
		List<Integer> coords = currentTetromino.getNextPositions();
		for(Integer coord : coords){
			if(coord % 100 > 19 || grid.get(coord) == true){
				return false;
			}
		}
		return true;
	}
	
	private void clearFullRows(){
		//check each of 20 rows
		for(int y = 0; y < 20; y++){
			boolean rowFull = true;
			for(int x = 0; x < 10; x++){
				Integer coord = x * 100 + y;
				if(grid.get(coord) == false){
					rowFull = false;
					break; //breaks inner loop only
				}
			}
			if(rowFull){
				for(int x = 0; x < 10; x++){
					Integer coord = x * 100 + y;
					grid.put(coord, false);
				}
				for(int i = y - 1; i > 0; i--){
					for(int x = 0; x < 10; x++){
						Integer coord = x * 100 + i;
						if(grid.get(coord) == true){
							grid.put(coord, false);
							grid.put(coord + 1, true);
						}
					}
				}
			}
		}
	}
	
	private Tetromino nextTetromino(){
		Random rand = new Random();
		int next = rand.nextInt(2);
		switch(next){
			case 0: return new OShapedTetromino();
			case 1: return new IShapedTetromino();
		}
		return null;
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		if(checkEmptyPath()){			
			currentTetromino.moveDown();
		}else{
			for(Integer coord : currentTetromino.getBlockPositions()){
				grid.put(coord, true);
			}
			currentTetromino = nextTetromino();
		}
		clearFullRows();
		repaint();
	}
	
	//bug: rotating the i-block when it first spawns
	//causes it to lock into place at the top of the screen
	//and spawn the next block
	//possibly due to the block having negative coordinates
	//when it rotates off the top of the screen
	@Override
	public void keyPressed(KeyEvent event) {
		int keyCode = event.getKeyCode();
		if(keyCode == KeyEvent.VK_UP){
			currentTetromino.rotate();
			repaint();
		}else if(keyCode == KeyEvent.VK_DOWN){
			if(checkEmptyPath()){			
				currentTetromino.moveDown();
				repaint();
			}			
		}else if(keyCode == KeyEvent.VK_LEFT){
			currentTetromino.moveLeft();
			repaint();
		}else if(keyCode == KeyEvent.VK_RIGHT){
			currentTetromino.moveRight();
			repaint();
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		//unused
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		//Unused		
	}
}
