package ascii.sprites;

import java.awt.Point;

public class Crystal extends Item{
	private static int crystalsUsed=0;
	
	public Crystal(Point pos) {
		super('§',pos,"It glows a purple color.",true,true);
		space=true;
		worth=500;
	}
	
	/**For instantiating in inventory
	 * 
	 */
	public Crystal() {
		super('§',new Point(0,0),"It glows a purple color.",true,false);
		worth=500;
	}
	
	public void use() {
		crystalsUsed++;
	}
	
	public static int crystalsUsed() {
		return crystalsUsed;
	}
	
	public static void set(int n) {
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
