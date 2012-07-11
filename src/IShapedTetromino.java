import java.awt.Color;
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
		colors[0] = new Color(102, 204, 255);
		colors[1] = new Color(153, 255, 255);
		colors[2] = new Color(151, 153, 204);
	}
	
	@Override
	protected List<Integer> wallKick(List<Integer> failedRotation, Map<Integer, Color[]> grid){
		List<Integer> newPosition = new ArrayList<Integer>();
		
		//ceiling kick
		boolean ceilingKick1 = false;
		boolean ceilingKick2 = false;
		List<Integer> kickDown = new ArrayList<Integer>();
		for(Integer block : failedRotation){
			if(block % 100 == 99){
				ceilingKick1 = true;
			}
			if(block % 100 == 98){
				ceilingKick2 = true;
			}
		}
		for(Integer block : failedRotation){
			if(ceilingKick2 == true){
				Integer newBlock = block + 2;
				kickDown.add(newBlock);
			}else{
				Integer newBlock = block + 1;
				kickDown.add(newBlock);
			}
		}
		if(ceilingKick1 == true || ceilingKick2 == true && checkLegalMove(kickDown, grid)){
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

	@Override
	public void rotate(Map<Integer, Color[]> grid) {
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
}
