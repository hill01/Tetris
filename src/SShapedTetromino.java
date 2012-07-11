import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class SShapedTetromino extends Tetromino{

	public SShapedTetromino(){
		blockPositions = new ArrayList<Integer>();
		Integer[] positions = {301, 400, 401, 500};
		for(int i = 0; i < 4; i++){
			blockPositions.add(positions[i]);
		}
		currentRotation = 0;
		colors[0] = new Color(204, 0, 205);
		colors[1] = new Color(204, 102, 255);
		colors[2] = new Color(153, 0, 204);
	}

	@Override
	public void rotate(Map<Integer, Color[]> grid) {
		Integer center = blockPositions.get(1);
		List<Integer> newPositions = new ArrayList<Integer>();
		if(currentRotation == 0){			
			newPositions.add(center - 1);
			newPositions.add(center);
			newPositions.add(center + 100);
			newPositions.add(center + 101);
			currentRotation = 1;
		}else{
			newPositions.add(center - 100 + 1);
			newPositions.add(center);
			newPositions.add(center + 1);
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
