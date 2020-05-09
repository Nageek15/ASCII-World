package ascii.sprites;

import java.awt.Point;

import ascii.App;
import gameutil.text.Console;

public class OrbOfHealing extends Item{
	public OrbOfHealing(Point pos) {
		super("Orb of Healing",'O',pos,"The smooth glass orb glows a green color.",true,true,true);
		space=false;
		worth=200;
	}
	
	public OrbOfHealing() {
		super("Orb of Healing",'O',new Point(0,0),"The smooth glass orb glows a green color.",true,true,true);
		space=false;
		inWorld=false;
		worth=200;
	}
	
	public void use() {
		Console.s.print("Your wounds heal");
		Console.s.println("The orb discintegrates...");
		//mhp
		App.p.setHealth(App.p.getHealth()+1+App.rand.nextInt(2)+App.rand.nextInt(App.maxDifficulty-App.getDifficulty()));
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
