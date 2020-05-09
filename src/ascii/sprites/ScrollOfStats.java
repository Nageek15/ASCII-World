package ascii.sprites;

import java.awt.Point;

import ascii.App;
import gameutil.text.Console;

public class ScrollOfStats extends Item{
	public ScrollOfStats(Point pos) {
		super("Scroll of Stats",'ƒ',pos,"It says some stuff...",true,true,false);
		space=true;
		worth=10;
	}
	
	public ScrollOfStats() {
		super("Scroll of Stats",'ƒ',new Point(0,0),"It says some stuff...",true,false,false);
		space=true;
	}
	
	@Override
	public void use() {
		Console.s.println("The scroll reads:");
		Console.s.println("");
		Console.s.println("Stats of "+App.p.getName()+":");
		Console.s.println("     HP: "+App.p.getHealth()+"/"+App.p.getMaxHealth());
		Console.s.println("     Letters: "+App.p.getLetters()+"L");
		Console.s.println("     Max Jump Height: "+App.p.getMaxJump());
		Console.s.println("     Lowest Audible VolumeL: "+App.p.getLowestAudibleVolume());
		Console.s.println("     Atk: "+App.p.getAtk());
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
