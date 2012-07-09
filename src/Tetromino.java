import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public abstract class Tetromino {
	protected List<Integer> blockPositions;
	protected int currentRotation;
		
	public List<Integer> getBlockPositions() {
		return blockPositions;
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
	
	public void moveDown(Map<Integer, Boolean> grid){
		List<Integer> newPositions = new ArrayList<Integer>();
		for(Integer block : blockPositions){
			Integer newBlock = block + 1;
			newPositions.add(newBlock);
		}
		if(checkLegalMove(newPositions, grid)){
			blockPositions = newPositions;
		}
	}
	
	public void moveLeft(Map<Integer, Boolean> grid){		
		List<Integer> newPositions = new ArrayList<Integer>();
		for(Integer block : blockPositions){
			Integer newBlock = block - 100;
			newPositions.add(newBlock);
		}
		if(checkLegalMove(newPositions, grid)){
			blockPositions = newPositions;
		}
	}
	
	public void moveRight(Map<Integer, Boolean> grid){
		List<Integer> newPositions = new ArrayList<Integer>();
		for(Integer block : blockPositions){
			Integer newBlock = block + 100;
			newPositions.add(newBlock);
		}
		if(checkLegalMove(newPositions, grid)){
			blockPositions = newPositions;
		}
	}
	
	public boolean checkLegalMove(List<Integer> newPositions, Map<Integer, Boolean> grid){
		for(Integer coord : newPositions){
			Integer x = coord / 100;
			Integer y = coord % 100;
			if(x < 0 || x > 9 || y < 0 || y > 19 || grid.get(coord) == true){
				return false;
			}
		}
		return true;		
	}
	
	public void rotate(Map<Integer, Boolean> grid){
		//implemented differently for each tetromino
	}

	
}
