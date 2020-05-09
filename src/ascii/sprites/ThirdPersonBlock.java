package ascii.sprites;

import java.awt.Point;

public class ThirdPersonBlock extends Sprite{
	public ThirdPersonBlock(Point pos) {
		super('3',pos);
		space=true;
		solid=false;
	}
	
	@Override
	public String getProps() {
		return "|/o\\| java.awt.Point |/p\\| int "+getX()+" int "+getY()+" |/e1\\|";//save in world
	}
}
