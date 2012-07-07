
public class Block {
	private int position;
	
	public Block(){
		position = 500;
	}
	
	public int getPosition(){
		return position;
	}
	
	public int getXPosition(){
		return position / 100;
	}
	
	public int getYPosition(){
		return position % 100;
	}
	
	public void setXPosition(int x){
		int ypos = position % 100;
		position = x * 100 + ypos;
	}
	
	public void setYPosition(int y){
		int xpos = position / 100;
		position = xpos * 100 + y;
	}
}
