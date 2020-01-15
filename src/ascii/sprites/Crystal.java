package ascii.sprites;

import java.awt.Point;

public class Crystal extends Item{
	private static int crystalsUsed=0;
	
	public Crystal(Point pos) {
		super('�',pos,"It glows a purple color.",true,true);
		space=true;
	}
	
	/**For instantiating in inventory
	 * 
	 */
	public Crystal() {
		super('�',new Point(0,0),"It glows a purple color.",true,false);
	}
	
	public void use() {
		crystalsUsed++;
	}
	
	public static int crystalsUsed() {
		return crystalsUsed;
	}
	
	public static void reset() {
		crystalsUsed=0;
	}
	
	@Override
	public String getProps() {
		if (inWorld) {
			return "|/o\\| java.awt.Point |/p\\| int "+getX()+" int "+getY()+" |/e1\\|";//save in world
		} else {
			return "";//save in inventory
		}
	}
}
