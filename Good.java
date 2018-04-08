public class Good extends Person{
	
	protected String name;
	
	public Good(String name, int range, int threatLevel) {
		super(range);
		this.name = name;
		this.threatLevel = threatLevel;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public String whoAmI(){
		return "good";
	}
}
