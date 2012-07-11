import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public abstract class Tetromino {
	protected List<Integer> blockPositions; //coords of each block, (x, y) = (coord / 100, coord % 100)
	protected int currentRotation;
	protected Color[] colors = new Color[3];
	//color[0] = main color
	//color[1] = highlight
	//color[2] = shading
		
	public List<Integer> getBlockPositions() {
		return blockPositions;
	}
	
	public Color[] getColors(){
		return colors;
	}

	//returns the positions immediately below the current blocks
	public List<Integer> getNextPositions() {
		List<Integer> nextPositions = new ArrayList<Integer>();
		for(Integer coord : blockPositions){
			//adding 1 increments the y-coordinate
			Integer nextPosition = coord + 1;
			nextPositions.add(nextPosition);
		}
		return nextPositions;
	}
	
	public void moveDown(Map<Integer, Color[]> grid){
		List<Integer> newPositions = new ArrayList<Integer>();
		for(Integer block : blockPositions){
			Integer newBlock = block + 1;
			newPositions.add(newBlock);
		}
		if(checkLegalMove(newPositions, grid)){
			blockPositions = newPositions;
		}
	}
	
	public void moveLeft(Map<Integer, Color[]> grid){		
		List<Integer> newPositions = new ArrayList<Integer>();
		for(Integer block : blockPositions){
			Integer newBlock = block - 100;
			newPositions.add(newBlock);
		}
		if(checkLegalMove(newPositions, grid)){
			blockPositions = newPositions;
		}
	}
	
	public void moveRight(Map<Integer, Color[]> grid){
		List<Integer> newPositions = new ArrayList<Integer>();
		for(Integer block : blockPositions){
			Integer newBlock = block + 100;
			newPositions.add(newBlock);
		}
		if(checkLegalMove(newPositions, grid)){
			blockPositions = newPositions;
		}
	}
	
	//returns false if any of the new coordinates are off the grid, or 
	//if any of them are already occupied by a locked-in block
	public boolean checkLegalMove(List<Integer> newPositions, Map<Integer, Color[]> grid){
		for(Integer coord : newPositions){
			Integer x = coord / 100;
			Integer y = coord % 100;
			if(x < 0 || x > 9 || y < 0 || y > 19 || grid.get(coord) != null){
				return false;
			}
		}
		return true;		
	}
	
	
	//when a tetromino attempts to rotate off the grid or through locked-in blocks,
	//the tetromino will 'kick' off the wall to the left or right if doing so is a legal move
	//if it can kick left or right, it will prefer to kick right
	//if no legal rotations exsist, even after attempting to kick, the method will
	//return the kick to the left, even if it's illegal. this is accounted for in the rotate method
	protected List<Integer> wallKick(List<Integer> failedRotation, Map<Integer, Color[]> grid){
		List<Integer> newPosition = new ArrayList<Integer>();
		
		//ceiling kick
		//allows tetrominos to rotate immediately after spawning
		boolean ceilingKick = false;
		List<Integer> kickDown = new ArrayList<Integer>();
		for(Integer block : failedRotation){
			if(block % 100 == 99){
				ceilingKick = true;
			}
			Integer newBlock = block + 1;
			kickDown.add(newBlock);
		}
		if(ceilingKick == true && checkLegalMove(kickDown, grid)){
			return kickDown;
		}
		
		//attempt to kick to the right
		for(Integer block : failedRotation){
			Integer newBlock = block + 100;
			newPosition.add(newBlock);
		}
		
		if(checkLegalMove(newPosition, grid)){
			return newPosition;
		}else{
			//kick to the left, returns even if its not a legal move
			newPosition = new ArrayList<Integer>();
			for(Integer block : failedRotation){
				Integer newBlock = block - 100;
				newPosition.add(newBlock);
			}
			return newPosition;
		}
	}
	
	public abstract void rotate(Map<Integer, Color[]> grid);
}
