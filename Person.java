public class Person {
	
	protected int range;
	protected int threatLevel;
	protected int xVal;
	protected int yVal;
	
	public Person (int range) {
		this.range = range;
	}
	
	public void setLocation(int xVal, int yVal) {
		this.xVal = xVal;
		this.yVal = yVal;
	}
	
	public int getRange() {
		return range;
	}
	
	public int getThreatLevel(){
		return threatLevel;
	}
	
	public int getXVal() {
		return xVal;
	}

	public int getYVal() {
		return yVal;
	}
	
	public String whoAmI(){
		return "";
	}
    
    public String getName() {
        return "Pedestrian";
    }

    public void updateLocation(int move, int dir) {
        // move: 0 (stay put), 1 (x-dir), 2 (y-dir)
        // dir: 0 (left/up), 1 (right/down)
        if(move == 1) { // x dir
            if(dir == 0 && xVal > 0) xVal--;
            else if(dir == 0 && xVal == 0) xVal++;
            else if(dir == 1 && xVal < 19) xVal++;
            else if(dir == 1 && xVal == 19) xVal--;
        } else if(move == 2) { // y dir
            if(dir == 0 && yVal > 0) yVal--;
            else if(dir == 1 && yVal == 0) yVal++;
            else if(dir == 1 && yVal < 19) yVal++;
            else if(dir == 1 && yVal == 19) yVal--;
        }
    }
	
}
