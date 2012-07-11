import java.awt.Color;
import java.util.ArrayList;
import java.util.Map;


public class OShapedTetromino extends Tetromino{
	
	public OShapedTetromino(){
		blockPositions = new ArrayList<Integer>();
		Integer[] positions = {400, 401, 500, 501};
		for(int i = 0; i < 4; i++){
			blockPositions.add(positions[i]);
		}
		//yellow
		colors[0] = new Color(255, 255, 0);
		colors[1] = new Color(255, 255, 153);
		colors[2] = new Color(255, 204, 0);
	}

	@Override
	public void rotate(Map<Integer, Color[]> grid) {
		// TODO Auto-generated method stub
		
	}
}
