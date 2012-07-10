import java.util.ArrayList;
import java.util.Map;


public class OShapedTetromino extends Tetromino{
	
	public OShapedTetromino(){
		blockPositions = new ArrayList<Integer>();
		Integer[] positions = {400, 401, 500, 501};
		for(int i = 0; i < 4; i++){
			blockPositions.add(positions[i]);
		}
	}

	@Override
	public void rotate(Map<Integer, Boolean> grid) {
	}
}
