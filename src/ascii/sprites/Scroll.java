package ascii.sprites;

import java.awt.Point;

import gameutil.text.Argument;
import gameutil.text.Console;

public class Scroll extends Item{
	
	String contents;
	
	public Scroll(String name,Point pos,String contents) {
		super(name,'�',pos,"It says some stuff...",true,true,false);
		space=true;
		this.contents=contents;
		worth=4;
	}
	
	public Scroll(String name, String contents) {
		super(name,'�',new Point(0,0),"It says some stuff...",true,false,false);
		space=true;
		this.contents=contents;
		worth=4;
	}
	
	@Override
	public void use() {
		Console.s.println("The scroll reads:");
		Console.s.println("");
		Console.s.println(contents);
		Console.s.pause();
	}
	
	@Override
	public String getProps() {
		if (inWorld) {
			return "String |/l\\| "+name+" |/eS\\| |/o\\| java.awt.Point |/p\\| int "+getX()+" int "+getY()+" |/e1\\| |/p\\| String |/l\\| "+Argument.encodeNewLine(contents)+" |/eS\\|";
		} else {
			return "String |/l\\| "+name+" |/eS\\| String |/l\\| "+Argument.encodeNewLine(contents)+" |/eS\\|";
		}
	}
}
