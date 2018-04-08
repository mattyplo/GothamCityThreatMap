public class Bad extends Person{
	
	protected String name;
	
	public Bad(String name, int range, int threatLevel) {
		super(range);
		this.name = name;
        this.threatLevel = threatLevel;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public String whoAmI(){
		return "bad";
	}
	
}

