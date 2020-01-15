package ascii;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;

import ascii.map.Map;
import ascii.sprites.Block;
import ascii.sprites.Crystal;
import ascii.sprites.Item;
import ascii.sprites.Player;
import ascii.sprites.Sprite;
import gameutil.text.Argument;
import gameutil.text.Console;

public class App {
	public static Map map;
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
					Console.s.clr();
					//title screen
					Console.s.println("Game Menu");
					Console.s.println("");
					Console.s.println("|ASCII World|");
					Console.s.println("");
					Console.s.println("Type 'play' to play or 'exit' to exit");
					String choice=Console.s.readLine();
					if (choice.equals("play")) {
						Console.s.clr();
						Console.s.println("Game Menu");
						Console.s.println("");
						Console.s.println("Type 'new' to start a new game or 'load' to resume a saved game.");
						choice=Console.s.readLine();
						if (choice.equals("new")) {
							state=GAME_STATE.intro;
						} else {
							//get directories
							File file = new File("saves");
							String[] directories = file.list(new FilenameFilter() {
							  @Override
							  public boolean accept(File current, String name) {
							    return new File(current, name).isDirectory();
							  }
							});
							if (directories.length>0) {
								boolean directoryFound=false;
								String saveDir="";
								while (!directoryFound) { //ask until user enters a valid directory
									//print save files and ask which one should be loaded if there are save files
									Console.s.clr();
									Console.s.println("Game Menu");
									Console.s.println("");
									Console.s.println(Arrays.toString(directories));
									Console.s.print("What save would you like to load (type name of save without brackets?)");
									choice=Console.s.readLine();
									for (String d:directories) {
										if (choice.equals(d)) {
											directoryFound=true;
											saveDir=d;
										}
									}
								}
								//load from save directory
								
								//load player
								String read="";
						        BufferedReader reader=null;
						        String path=saveDir+"/dat/player.dat";
						        try {
						            reader=new BufferedReader(new FileReader(path));
						        } catch (FileNotFoundException e) {
						            //level is not saved, so keep the level the way it is and return
						            Console.s.println("No information to load for level at path: "+path);
						            
						        }
						        
						        try {
						        	//read player properties (name, pos x and y, hp, mhp, jump, max jump, jump horizontal velocity, jumping)
						        	p=new Player(reader.readLine(),new Point(Integer.parseInt(reader.readLine()),Integer.parseInt(reader.readLine())),Integer.parseInt(reader.readLine()),Integer.parseInt(reader.readLine()),Integer.parseInt(reader.readLine()),Integer.parseInt(reader.readLine()),Integer.parseInt(reader.readLine()),reader.readLine().equals("true"));
						        	int line=9;
						        	//read inventory contents
						            while (!read.equals("|i|")) {
						                read = reader.readLine();
						                Argument args=Argument.getArgs(read);
						                if (args.cmd().equals("I")){
						                    args=Argument.getArgs(read.substring(2));
						                    p.getInventory().add((Item) Argument.constructFromArgs(args));
						                }
						                line++;
						            }
						        } catch (IOException e) {
						            Console.s.println("Error! Failed to load player because IO exception!");
						            try {
						                Thread.sleep(2000);
						            } catch (InterruptedException e1) {
						                e1.printStackTrace();
						            }
						            System.exit(1);
						        }
						        //read the next line
						        try {
						            read=reader.readLine();
						        } catch (IOException e) {
						            e.printStackTrace();
						            //stop reading
						            read="|i|";
						        }
								
								//load levels
									
									
								
								Console.s.pause();
							} else {
								//if there were no save files found then notify the user and return to title Screen.
								Console.s.println("No save files were found");
								Console.s.pause();
							}
						}
					} else if (choice.equals("exit")){
						state=GAME_STATE.exit;
					}
				break;
				case load:
					//load the stuffs
				break;
				case intro:
					Console.s.clr();
					Console.s.print("What is your name? ");
					String name=Console.s.readLine();
					//create player
					p=new Player(name);
					//intro text
					Console.s.println("You are about to appear in ASCII World. Type 'use \"Scroll of Grabbing\" for help, remember this cause this is the only time that you can read this.");
					//get game ready
					map=new Map();
					state=GAME_STATE.inGame;
					Console.s.pause();
				break;
				case inGame:
					//update
					p.update();
					if (p.inWorld()==false||p.getY()<0){
						Console.s.clr();
						Console.s.println("GAME OVER: YOU ARE NO LONGER IN ASCII WORLD");
						Console.s.pause();
						state=GAME_STATE.menu;
						break;
					} else if (Crystal.crystalsUsed()==2) {
						Console.s.clr();
						Console.s.println("YOU WIN!");
						Console.s.pause();
						state=GAME_STATE.menu;
					}
					map.getCamera().location=p.getPos();
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
						case "exit":
							state=GAME_STATE.exit;
						break;
						case "walk":
							switch (cmd.get(1)) {
								case "right":
									p.walk(1);
								break;
								case "left":
									p.walk(-1);
								break;
							}
						break;
						case "brincar":
							switch (cmd.get(1)) {
								default:
									p.jump(0);
								break;
								case "right":
									p.jump(1);
								break;
								case "left":
									p.jump(-1);
								break;
							}
						break;
						case "grab":
							p.pickup();
						break;
						case "inv":
							p.getInventory().printContents();
							Console.s.pause();
						break;
						case "use":
							String itemName=cmd.get(1);
							System.out.println("itemName:"+itemName);
							int amount;
							try {
								//attempt to use second arg for amount to use
								amount=Integer.parseInt(cmd.get(2));
							} catch (NumberFormatException e) {
								//use only one
								amount=1;
							}
							p.getInventory().use(itemName, amount);
						break;
						case "save":
							save();
						break;
						case "camera":
							Console.s.println("Camera X: "+map.getCamera().location.x);
							Console.s.println("Camera Y: "+map.getCamera().location.y);
							Console.s.pause();
						break;
						
						case "fork":
							Console.s.println("Daniah stop swearing please.");
							Console.s.println(p.getName()+", you too.");
							Console.s.pause();
						break;
						case "fly":
							Console.s.println("No.");
							Console.s.pause();
						break;
						case "spell":
							switch (cmd.get(1)){
								case "please fly":
									switch (cmd.get(2)) {
										case "right":
											p.attemptMove(new Point(p.getX()+1,p.getY()));
										break;
										case "left":
											p.attemptMove(new Point(p.getX()-1,p.getY()));
										break;
										case "up":
											p.attemptMove(new Point(p.getX(),p.getY()+1));
										break;
										case "down":
											p.attemptMove(new Point(p.getX(),p.getY()-1));
										break;
									}
								break;
							}
					}
					
					
				break;
			}
		}
		
		System.exit(0);
	}
	
	public void save() {
		String path="saves/"+p.getName()+"/";
		new File(path);
		new File(path+"dat");
		new File(path+"levels");
		//save levels
		map.saveLevels(path);
		
		BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter(path+"player.dat"));
			//write stuff to player data
			writer.write("");
			writer.write("||");
	        writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
			
        
	}

}
