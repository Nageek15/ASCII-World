package ascii.sprites;

import java.awt.Point;

public class FirstPersonBlock extends Sprite{
	public FirstPersonBlock(Point pos) {
		super('1',pos);
		space=true;
		solid=false;
	}
	
	@Override
	public String getProps() {
		return "|/o\\| java.awt.Point |/p\\| int "+getX()+" int "+getY()+" |/e1\\|";//save in world
	}
}
