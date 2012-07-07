import java.util.ArrayList;
import java.util.List;


public class IShapedTetromino extends Tetromino{
	private int position = 0;
	
	public IShapedTetromino(){
		blockPositions = new ArrayList<Integer>();
		Integer[] positions = {300, 400, 500, 600};
		for(int i = 0; i < 4; i++){
			blockPositions.add(positions[i]);
		}
	}
	@Override
	public void rotate() {
		Integer center = blockPositions.get(2);
		List<Integer> newPositions = new ArrayList<Integer>();
		if(position == 0){			
			newPositions.add(center - 2);
			newPositions.add(center - 1);
			newPositions.add(center);
			newPositions.add(center + 1);
			position = 1;
		}else{
			newPositions.add(center - 200);
			newPositions.add(center - 100);
			newPositions.add(center);
			newPositions.add(center + 100);
			position = 0;
		}
		blockPositions = newPositions;
	}

}
