package ascii.sprites;

import java.awt.Point;

import gameutil.text.Console;

public class Scroll extends Item{
	
	String contents;
	
	public Scroll(String name,Point pos,String contents) {
		super(name,'ƒ',pos,"It says some stuff...",true,true);
		space=true;
		this.contents=contents;
	}
	
	public Scroll(String name, String contents) {
		super(name,'ƒ',new Point(0,0),"It says some stuff...",true,false);
		space=true;
		this.contents=contents;
	}
	
	@Override
	public void use() {
		Console.s.println("The scroll reads:");
		Console.s.println("");
		Console.s.println(contents);
		Console.s.pause();
	}
}
