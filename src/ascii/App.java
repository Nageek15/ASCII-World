package ascii;

import java.awt.Point;

import ascii.map.Map;
import ascii.sprites.Player;
import gameutil.text.Argument;
import gameutil.text.Console;

public class App {
	private Map map;
	private enum GAME_STATE {menu,intro,load,inGame,exit};
	private GAME_STATE state=GAME_STATE.menu;
	public static Player p;
	
	
	public static void main(String[] unicorns) {
		new App();
	}
	
	public App() {
		Console.s.setTheme(Console.theme.pink);
		Console.s.print("");
		while (state!=GAME_STATE.exit) {
			switch (state) {
				case menu:
					Console.s.println("Type 'play' to play or 'exit' to exit");
					//check for save file
					boolean savefile=false;
					if (savefile) {
						state=GAME_STATE.load;
					} else {
						state=GAME_STATE.intro;
					}
				break;
				case load:
					//load the stuffs
				break;
				case intro:
					Console.s.print("What is your name?");
					String name=Console.s.readLine();
					//create player
					p=new Player(name);
					//intro text
					Console.s.println("You are about to appear in ANSCII World. Type ? for help.");
					//get game ready
					map=new Map();
					state=GAME_STATE.inGame;
					Console.s.pause();
				break;
				case inGame:
					//update
					//map.getCamera().location=p.getPos();
					map.update();
					
					//draw
					Console.s.clr();
					map.drawMap(Console.s);
					//write
					Console.s.println("");
					//input
					Argument cmd=Argument.getArgs(Console.s.readLine());
					//respond
					switch (cmd.cmd()) {
						case "?":
							//display help
						break;
						case "exit":
							state=GAME_STATE.exit;
						break;
						case "walk":
							switch (cmd.get(1)) {
								case "right":
									p.setPos(new Point(p.getPos().x+1,p.getPos().y));
								break;
								case "left":
									p.setPos(new Point(p.getPos().x-1,p.getPos().y));
								break;
							}
						break;
						case "camera":
							Console.s.println("Camera X: "+map.getCamera().location.x);
							Console.s.println("Camera Y: "+map.getCamera().location.y);
							Console.s.pause();
						break;
					}
					
					
				break;
			}
		}
		
		System.exit(0);
	}
	
	public void save() {
		
	}

}
