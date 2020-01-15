package ascii.sprites;

import java.awt.Point;

public class Crystal extends Item{
	public Crystal(Point pos) {
		super('§',pos,"It glows a purple color.",false,true);
		space=true;
	}
}
