import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.Timer;


@SuppressWarnings("serial")
public class TetrisPane extends JPanel implements ActionListener, KeyListener{
	
	private static Timer timer;
	private static int blockSize = 20; //determines the size of the panel
	private static int width;
	private static int height;
	private static int speed; //length of one "turn", in milliseconds
	private static boolean lockInDelay; //if true block wont lock in
	private static int tetrominoCount; //counts how far into randomGenerator you are
	private static int level;
	private static int score;
	private static int linesCleared;
	private static Tetromino currentTetromino;
	private static Tetromino nextTetromino;
	List<Tetromino> randomGenerator = new ArrayList<Tetromino>(); //bag of 7 all tetrominos
	InfoPane infoPane;
	
	//Integer represents coordinates, the 100 digits represent x coordinates 0-9
	//10 and 1 digits represent y coordinates 0-19
	//e.g. 812 represents the (x, y)-coordinate (8, 12)
	//x = Integer / 100, y = Integer % 100
	//grid contains only locked in blocks, not the active tetromino
	//if the Color[] value is null, then the space is unoccupied
	private static Map<Integer, Color[]> grid = new HashMap<Integer, Color[]>();
	
	
	public TetrisPane(InfoPane pane){
		super();
		width = blockSize * 10;
		height = blockSize * 20;
		setPreferredSize(new Dimension(width, height));
		setFocusable(true);
		addKeyListener(this);
		speed = 500; 
		timer = new Timer(speed, this);
		timer.setInitialDelay(speed);
		timer.start();
		lockInDelay = true;
		tetrominoCount = 0;
		level = 0;
		score = 0;
		linesCleared = 0;	
		infoPane = pane;
		
		randomGenerator.add(new IShapedTetromino());
		randomGenerator.add(new JShapedTetromino());
		randomGenerator.add(new LShapedTetromino());
		randomGenerator.add(new SShapedTetromino());
		randomGenerator.add(new ZShapedTetromino());
		randomGenerator.add(new OShapedTetromino());
		randomGenerator.add(new TShapedTetromino());
		Collections.shuffle(randomGenerator);
		
		currentTetromino = nextTetromino();
		nextTetromino = nextTetromino();
		infoPane.setNextTetromino(nextTetromino);
		
		//null value means the space is unoccupied
		for(int i = 0; i < 10; i++){
			for(int j = 0; j < 20; j++){
				Integer coords = (i * 100) + j;
				grid.put(coords, null);
			}
		}
	}
	
	//draws one block. coord is the blocks coordinates, 
	//colors is the 3 colors for the block, the main color, highlight, and shadow
	private void drawBlock(Integer coord, Color[] colors, Graphics g){
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
	
	//called (by calling repaint()) once a 'turn', or any time an arrow key is pressed
	public void paintComponent(Graphics g){
		//clear the pane with background color
		//alternate bg color new Color(218, 227, 235) light gray
		//new Color(0, 0, 51) near black
		g.setColor(new Color(218, 227, 235));
		g.fillRect(0, 0, width, height);
		
		//draw all the current locked in blocks
		for(Integer coord : grid.keySet()){
			if(grid.get(coord) != null){
				drawBlock(coord, grid.get(coord), g);
			}
		}
		
		//draw the current tetromino
		for(Integer coord : currentTetromino.getBlockPositions()){
			drawBlock(coord, currentTetromino.getColors(), g);
		}
		
		//draw border
		g.setColor(new Color(0, 0, 51));
		g.drawRect(0, 0, width - 1, height - 1);
	}

	//leveling up, speed increase and some scoring occur in this method
	private void clearFullRows(){
		int rowsCleared = 0;
		
		//check each of 20 rows
		for(int y = 0; y < 20; y++){
			boolean rowFull = true;
			//check each block in the current row
			for(int x = 0; x < 10; x++){
				Integer coord = x * 100 + y;
				if(grid.get(coord) == null){
					rowFull = false;
					break; //breaks inner loop only
				}
			}
			if(rowFull){
				rowsCleared++;
				//clears each block in the current row
				for(int x = 0; x < 10; x++){
					Integer coord = x * 100 + y;
					grid.put(coord, null);
				}
				//moves each block above the cleared row down a space
				for(int i = y - 1; i > 0; i--){
					for(int x = 0; x < 10; x++){
						Integer coord = x * 100 + i;
						if(grid.get(coord) != null){
							grid.put(coord + 1, grid.get(coord));
							grid.put(coord, null);
						}
					}
				}
			}
		}		
		//scoring
		switch(rowsCleared){
			case 1: score += 40 * (level + 1);
					break;
			case 2: score += 100 * (level + 1);
					break;
			case 3: score += 300 * (level + 1);
					break;
			case 4: score += 1200 * (level + 1);
					break;
		}
		linesCleared += rowsCleared;
		level = linesCleared / 10;
		speed = Math.max(500 - 50 * level, 100);
		timer.setDelay(speed);
		
		//update the info pane
		infoPane.setLevel(level);
		infoPane.setScore(score);
		infoPane.setLinesCleared(linesCleared);
	}
	
	//returns true if the game is over
	//currently defined as any of the possible top row spawn locations being occupied
	private boolean gameOver(){
		if(grid.get(300) != null || grid.get(400) != null || grid.get(500) != null || grid.get(600) != null){
			return true;
		}else{
			return false;
		}
	}
	
	//returns the next tetromino in the shuffled bag of tetrominos
	//the bag contains one of each possible tetrominos
	private Tetromino nextTetromino(){
		Tetromino next = randomGenerator.get(tetrominoCount);
		tetrominoCount++;
		
		//create and shuffle a new bag of all 7 tetrominos
		if(tetrominoCount > 6){
			tetrominoCount = 0;
			randomGenerator.clear();
			randomGenerator.add(new IShapedTetromino());
			randomGenerator.add(new JShapedTetromino());
			randomGenerator.add(new LShapedTetromino());
			randomGenerator.add(new SShapedTetromino());
			randomGenerator.add(new ZShapedTetromino());
			randomGenerator.add(new OShapedTetromino());
			randomGenerator.add(new TShapedTetromino());
			Collections.shuffle(randomGenerator);
		}
		return next;
	}
	
	//this method is called once every 'turn' (based on the Timer, every 'speed' milliseconds)
	//locking blocks into place occurs in this method
	//some score increase occurs here
	@Override
	public void actionPerformed(ActionEvent event) {
		if(gameOver()){
			//do some game over stuff
		}
		if(currentTetromino.checkLegalMove(currentTetromino.getNextPositions(), grid)){			
			currentTetromino.moveDown(grid);
			score += 1;
		}else if(lockInDelay){
			//delays lock in by one 'turn'
			lockInDelay = false;
		}else{
			//this is where the current piece gets locked into place
			for(Integer coord : currentTetromino.getBlockPositions()){
				grid.put(coord, currentTetromino.getColors());
			}
			clearFullRows();
			currentTetromino = nextTetromino;
			nextTetromino = nextTetromino();
			infoPane.setNextTetromino(nextTetromino);
			lockInDelay = true;
		}
		repaint();
	}
	
	//some score increase occurs here too
	@Override
	public void keyPressed(KeyEvent event) {
		int keyCode = event.getKeyCode();
		if(keyCode == KeyEvent.VK_UP){
			currentTetromino.rotate(grid);
			lockInDelay = true;
			repaint();
		}else if(keyCode == KeyEvent.VK_DOWN){
			if(currentTetromino.checkLegalMove(currentTetromino.getNextPositions(), grid)){			
				currentTetromino.moveDown(grid);
				lockInDelay = false;
				score += 1;
				repaint();
			}			
		}else if(keyCode == KeyEvent.VK_LEFT){
			currentTetromino.moveLeft(grid);
			lockInDelay = true;
			repaint();
		}else if(keyCode == KeyEvent.VK_RIGHT){
			currentTetromino.moveRight(grid);
			lockInDelay = true;
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
