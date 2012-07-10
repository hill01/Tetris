import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class IShapedTetromino extends Tetromino{
	
	public IShapedTetromino(){
		blockPositions = new ArrayList<Integer>();
		Integer[] positions = {300, 400, 500, 600};
		for(int i = 0; i < 4; i++){
			blockPositions.add(positions[i]);
		}
		currentRotation = 0;
	}
	
	@Override
	public void rotate(Map<Integer, Boolean> grid){
		Integer center = blockPositions.get(2);
		List<Integer> newPositions = new ArrayList<Integer>();
		if(currentRotation == 0){			
			newPositions.add(center - 2);
			newPositions.add(center - 1);
			newPositions.add(center);
			newPositions.add(center + 1);
			currentRotation = 1;
		}else{
			newPositions.add(center - 200);
			newPositions.add(center - 100);
			newPositions.add(center);
			newPositions.add(center + 100);
			currentRotation = 0;
		}
		if(checkLegalMove(newPositions, grid)){
			blockPositions = newPositions;
		}else{
			newPositions = wallKick(newPositions, grid);
			if(checkLegalMove(newPositions, grid)){
				blockPositions = newPositions;
			}
		}
	}
	
	@Override
	protected List<Integer> wallKick(List<Integer> failedRotation, Map<Integer, Boolean> grid){
		List<Integer> newPosition = new ArrayList<Integer>();
		//attempt to kick to the right
		for(Integer block : failedRotation){
			Integer newBlock = block + 100;
			newPosition.add(newBlock);
		}
		
		if(checkLegalMove(newPosition, grid)){
			return newPosition;
		}else{
			//try to kick 2 spaced to the right
			newPosition = new ArrayList<Integer>();
			for(Integer block : failedRotation){
				Integer newBlock = block + 200;
				newPosition.add(newBlock);
			}
			if(checkLegalMove(newPosition, grid)){
				return newPosition;
			}
			
			//kick to the left, returns even if its not a legal move
			newPosition = new ArrayList<Integer>();
			for(Integer block : failedRotation){
				Integer newBlock = block - 100;
				newPosition.add(newBlock);
			}
			return newPosition;
		}
	}

}
