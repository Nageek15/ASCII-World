package anscii;

import gameutil.text.Console;

public class App {

	enum GAME_STATE {menu,intro,load,inGame,exit};
	private GAME_STATE state=GAME_STATE.menu;
	
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
					//intro text
					Console.s.print("You are about to appear in ANSCII World. Type ? for help.");
					//get game ready
					
					state=GAME_STATE.inGame;
				break;
				case inGame:
					//update
					//draw
					//write
					//input
					//respond
					
				break;
			}
		}
	}

}
