package entity.player;

public enum Direction {
	clockwise,
	anticlockwise;
	
	public static String getDescription(Direction d){
		String[] description = {"À≥ ±’Î","ƒÊ ±’Î"};
		return description[d.ordinal()];
	}
	
	public static Direction counter(Direction d){
		switch(d){
		case clockwise:		return anticlockwise;
		case anticlockwise: return clockwise;
		default:
			return null;
		}
	}
}
