package ascii.sprites;

import java.awt.Point;

public class Crystal extends Item{
	private static int crystalsUsed=0;
	
	public Crystal(Point pos) {
		super('§',pos,"It glows a purple color.",true,true);
		space=true;
	}
	
	public void use() {
		crystalsUsed++;
	}
	
	public static int crystalsUsed() {
		return crystalsUsed;
	}
}
