package ascii.sprites;

import java.awt.Point;

public class Block extends Sprite{
	public Block(Point pos) {
		super('█',pos);
	}
	
	@Override
	public String getProps() {
		return "|/o\\| java.awt.Point |/p\\| int "+pos.x+" int "+pos.y+" |/e1\\|";
	}
}
