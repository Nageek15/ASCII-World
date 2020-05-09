package ascii.sprites;

import java.awt.Point;

import ascii.App;
import gameutil.text.Console;

public class Teleporter extends Item{
	int uses;
	
	public Teleporter(Point pos,int uses) {
		super('T',pos,"Gray dust.",true,true);
		space=false;
		worth=1500;
		this.uses=uses;
		if (uses>1) {
			consumable=false;
		} else {
			consumable=true;
		}
	}
	
	public Teleporter() {
		super('T',new Point(0,0),"Gray dust.",true,true);
		space=false;
		inWorld=false;
		worth=1500;
		uses=3;
	}
	
	public Teleporter(int uses) {
		super('T',new Point(0,0),"Gray dust.",true,true);
		space=false;
		inWorld=false;
		worth=1500;
		uses=uses;
	}
	
	public Teleporter(Point pos) {
		super('T',pos,"Gray dust.",true,true);
		space=false;
		inWorld=true;
		worth=1500;
		uses=3;
	}
	
	public void use() {
		if (consumable) {
			//go to highest level
			App.map.setLevel(App.p.getHighestLevel());
			Console.s.println("The teleporter uses it's last ounce of energy to bring you back to the highest level that you have been to.");
			Console.s.println("In doing so the teleporter breaks");
		} else {
			Console.s.print("Level: ");
			int lv=Console.s.readInt();
			lv--;//system level documentation starts at 0
			if (lv>=0 &&lv<=App.p.getHighestLevel()) {
				uses--;
				if (uses<=1) {
					consumable=true;
				}
				App.p.setPos(new Point(0,5));//reset player position
				App.map.setLevel(lv);//go to next level if it isn't the last level
				Console.s.println("You are swept away to another level...");
				Console.s.println("The number on the teleporter display now says ["+uses+"].");
				Console.s.pause();
			} else {
				Console.s.println("The teleporter beeps.");
				Console.s.println("The number on the teleporter display still reads ["+uses+"].");
				Console.s.pause();
			}
		}
	}
	
	@Override
	public String getProps() {
		if (inWorld) {
			return "|/o\\| java.awt.Point |/p\\| int "+getX()+" int "+getY()+" |/e1\\|";//save in world
		} else {
			return "int "+uses;//save in inventory
		}
	}
}
