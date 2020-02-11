package ascii.sprites;

import java.awt.Point;

public class Spike extends Sprite{
	public Spike(Point pos) {
		super('x',pos);
		solid=false;
		space=true;
	}
	
	public String getProps() {
		return "|/o\\| java.awt.Point |/p\\| int "+pos.x+" int "+pos.y+" |/e1\\|";
	}
}
