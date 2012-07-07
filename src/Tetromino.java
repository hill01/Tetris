import java.util.ArrayList;
import java.util.List;


public abstract class Tetromino {
	protected List<Integer> blockPositions;
		
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
	
	public void moveDown(){
		boolean isAtBottom = false;
		for(Integer block : blockPositions){
			if(block % 100 == 19){
				isAtBottom = true;
			}
		}
		if(!isAtBottom){
			List<Integer> newPositions = new ArrayList<Integer>();
			for(Integer block : blockPositions){
				Integer newBlock = block + 1;
				newPositions.add(newBlock);
			}
			blockPositions = newPositions;
		}
	}
	
	public void moveLeft(){
		boolean isAtEdge = false;
		for(Integer block : blockPositions){
			if(block / 100 == 0){
				isAtEdge = true;
			}
		}
		if(!isAtEdge){
			List<Integer> newPositions = new ArrayList<Integer>();
			for(Integer block : blockPositions){
				Integer newBlock = block - 100;
				newPositions.add(newBlock);
			}
			blockPositions = newPositions;
		}
	}
	
	public void moveRight(){
		boolean isAtEdge = false;
		for(Integer block : blockPositions){
			if(block / 100 == 9){
				isAtEdge = true;
			}
		}
		if(!isAtEdge){
			List<Integer> newPositions = new ArrayList<Integer>();
			for(Integer block : blockPositions){
				Integer newBlock = block + 100;
				newPositions.add(newBlock);
			}
			blockPositions = newPositions;
		}
	}	

	public abstract void rotate();
	
}
