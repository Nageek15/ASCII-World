package ascii.sprites;

import java.awt.Point;

import ascii.App;
import gameutil.text.Console;

public class Dust extends Item{
	public Dust(Point pos) {
		super('D',pos,"Gray dust.",true,true);
		space=false;
		worth=0;
	}
	
	public Dust() {
		super('D',new Point(0,0),"Gray dust.",true,true);
		space=false;
		inWorld=false;
		worth=1;
	}
	
	public void use() {
		Console.s.println("You sneeze, blowing all the dust away.");
		Console.s.pause();
		Console.s.println("A scroll appears in your hands in a magical flash of blue light.");
		App.p.getInventory().add(new ScrollOfStats());
		Console.s.pause();
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
