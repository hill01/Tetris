import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class ZShapedTetromino extends Tetromino {

	public ZShapedTetromino(){
		blockPositions = new ArrayList<Integer>();
		Integer[] positions = {300, 400, 401, 501};
		for(int i = 0; i < 4; i++){
			blockPositions.add(positions[i]);
		}
		currentRotation = 0;
		//orange
		colors[0] = new Color(255, 102, 0);
		colors[1] = new Color(255, 153, 51);
		colors[2] = new Color(204, 102, 0);
	}

	@Override
	public void rotate(Map<Integer, Color[]> grid) {
		Integer center;
		List<Integer> newPositions = new ArrayList<Integer>();
		if(currentRotation == 0){
			center = blockPositions.get(1);
			newPositions.add(center);
			newPositions.add(center + 1);
			newPositions.add(center + 100);
			newPositions.add(center + 100 - 1);
			currentRotation = 1;
		}else{
			center = blockPositions.get(0);
			newPositions.add(center - 100);
			newPositions.add(center);
			newPositions.add(center + 1);
			newPositions.add(center + 100 + 1);
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
