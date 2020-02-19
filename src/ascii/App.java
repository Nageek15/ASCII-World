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
import ascii.sprites.Bullet;
import ascii.sprites.Crystal;
import ascii.sprites.Enemy;
import ascii.sprites.Item;
import ascii.sprites.Player;
import ascii.sprites.Scroll;
import ascii.sprites.Spike;
import ascii.sprites.Sprite;
import gameutil.geom.g2D.VectorR2;
import gameutil.text.Argument;
import gameutil.text.Console;

public class App {
	public static Map map;
	private enum GAME_STATE {menu,intro,load,inGame,edit,exit};
	private GAME_STATE state=GAME_STATE.menu;
	public static Player p;
	public static Sounds sound=new Sounds();
	private boolean music=true;
	private boolean fp=false;
	
	//Always gotta have that unicorns array of Strings which are arguments applied to the command to run the jar.
	public static void main(String[] unicorns) {
		new App();
	}
	
	public App() {
		sound.setSourcePath("music/");
		Console.s.setTheme(Console.theme.pink);
		Console.s.print("");
		sound.playSoundOnLoop("Track.wav", 1);
		toggleMusic();
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
						} else if (choice.equals("load")){
							state=GAME_STATE.load;
						}
					} else if (choice.equals("exit")){
						state=GAME_STATE.exit;
					} else if (choice.equals("edit the yams")) {
						map=new Map("levels",0);
						p=new Player("Keegan");
						Console.s.setUserNextLineEnabled(true);
						state=GAME_STATE.edit;
						map.setEditMode(true);
					} else if (choice.equals("music")) {
						toggleMusic();
					}
				break;
				case load:
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
							Console.s.print("What save would you like to load (type name of save without brackets)?");
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
				        String path="saves/"+saveDir+"/player.dat";
				        try {
				            reader=new BufferedReader(new FileReader(path));
				        } catch (FileNotFoundException e) {
				            //player is not saved, so keep the level the way it is and return
				            Console.s.println("No information to load for player at path: "+path);
				            Console.s.println("Press any key to continue.");
				            state=GAME_STATE.menu;
				            Console.s.pause();
				            break;
				        }
				        
				        try {
				        	//read player properties (name, pos x and y, hp, mhp, jump, max jump, jump horizontal velocity, jumping)
				        	p=new Player(reader.readLine(),new Point(Integer.parseInt(reader.readLine()),Integer.parseInt(reader.readLine())),Integer.parseInt(reader.readLine()),Integer.parseInt(reader.readLine()),Integer.parseInt(reader.readLine()),Integer.parseInt(reader.readLine()),Integer.parseInt(reader.readLine()),reader.readLine().equals("true"));
				        	
				        	//load levels
				        	int lvNo=Integer.parseInt(reader.readLine());							
							map=new Map("saves/"+saveDir+"/levels",lvNo);
							int noCrystalsUsed=Integer.parseInt(reader.readLine());
							Crystal.set(noCrystalsUsed);
							//load inventory
				        	int line=11;
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
						
						
						Console.s.pause();
					} else {
						//if there were no save files found then notify the user and return to title Screen.
						Console.s.println("No save files were found");
						state=GAME_STATE.menu;
						Console.s.pause();
					}
					state=GAME_STATE.inGame;
				break;
				case intro:
					Console.s.clr();
					Console.s.print("What is your name? ");
					String name=Console.s.readLine();
					//create player
					p=new Player(name);
					//intro text
					Console.s.println("You are about to appear in ASCII World.");
					//get game ready
					map=new Map("levels",0);
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
						Crystal.set(0);//reset crystals used
						break;
					} else if (Crystal.crystalsUsed()>=map.currentLevel.crystalsToWin()) {
						if (map.isLastLevel()) {//if it's the last level then you win!
							Console.s.clr();
							Console.s.println("YOU WIN!");
							Console.s.pause();
							state=GAME_STATE.menu;
							Crystal.set(0);//reset crystals used
						} else {
							p.setPos(new Point(0,5));//reset player position
							Crystal.set(0);//reset crystals used
							map.setLevel(map.getLvNo()+1);//go to next level if it isn't the last level
						}
					}
					map.getCamera().location=p.getPos();
					map.update();
					
					//draw
					Console.s.clr();
					if (fp) {
						map.drawMapFP(Console.s);
					} else {
						map.drawMap(Console.s);
					}
					//write
					Console.s.println("HP: "+p.getHealth()+"/"+p.getMaxHealth());
					Console.s.println("Type ? for basic help.");
					//input
					Argument cmd=Argument.getArgs(Console.s.readLine());
					//respond
					switch (cmd.cmd()) {
						case "?":
							Console.s.println("To begin, use your Scroll of Grabbing.");
							Console.s.println("The commands are case sensitive");
							Console.s.println("");
							Console.s.println("Commands:");
							Console.s.println("use <item> - use an item (use quotes for item's that have names with spaces)");
							Console.s.println("  ex: use \"Scroll of Grabbing\"");
							Console.s.println("menu - return to main menu");
							Console.s.println("save - save the game");
							Console.s.println("exit - save the game");
							Console.s.pause();
						break;
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
							int jumpAmount=3;
							try {
								jumpAmount=Integer.parseInt(cmd.get(2));
							} catch (NumberFormatException e) {
								
							}
							switch (cmd.get(1)) {
								default:
									p.jump(0,jumpAmount);
								break;
								case "right":
									p.jump(1,jumpAmount);
								break;
								case "left":
									p.jump(-1,jumpAmount);
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
						case "menu":
							state=GAME_STATE.menu;
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
						case "fuck":
							Console.s.println("Daniah stop swearing please.");
							Console.s.println(p.getName()+", you too.");
							Console.s.pause();
						break;
						case "shit":
							Console.s.println("Daniah stop swearing please.");
							Console.s.println(p.getName()+", you too.");
							Console.s.pause();
						break;
						case "ass":
							Console.s.println("Daniah stop swearing please.");
							Console.s.println(p.getName()+", you too.");
							Console.s.pause();
						break;
						case "follate":
							Console.s.println("Daniah stop swearing please.");
							Console.s.println(p.getName()+", you too.");
							Console.s.pause();
						break;
						case "flapdoodle":
							Console.s.println("Daniah stop swearing please.");
							Console.s.println(p.getName()+", you too.");
							Console.s.pause();
						break;
						case "mierda":
							Console.s.println("Daniah stop swearing please.");
							Console.s.println(p.getName()+", you too.");
							Console.s.pause();
						break;
						case "fly":
							Console.s.println("No.");
							Console.s.pause();
						break;
						case "platitudinous":
							Console.s.println("That's a good word.");
							Console.s.pause();
						break;
						case "shoot":
							boolean add=false;
							VectorR2 velocity=null;
							Point pos=null;
							switch (cmd.get(1)) {
								case "left":
									try {
										velocity=new VectorR2(-1,0);
									} catch (Exception e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
									pos=new Point(p.getPos().x-1,p.getPos().y);
									add=true;
								break;
								case "right":
									try {
										velocity=new VectorR2(1,0);
									} catch (Exception e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
									pos=new Point(p.getPos().x+1,p.getPos().y);
									add=true;
								break;
								case "up":
									try {
										velocity=new VectorR2(0,1);
									} catch (Exception e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
									pos=new Point(p.getPos().x,p.getPos().y+1);
									add=true;
								break;
								case "down":
									try {
										velocity=new VectorR2(0,-1);
									} catch (Exception e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
									pos=new Point(p.getPos().x,p.getPos().y-1);
									add=true;
								break;
								
							}
							if (add) {
								Map.getCurrentLevel().add(new Bullet(new Point(p.getPos()),velocity,1));
							}
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
								case "test yams":
									try {
										map.setLevel(Integer.parseInt(cmd.get(2)));
									} catch (Exception e) {
										
									}
								break;
							}
						break;
						case "music":
							toggleMusic();
						break;
						case "fp":
							fp=!fp;
						break;
						case "turn":
							p.turn();
						break;
					}
					
					
				break;
				case edit:
					map.getCamera().location=p.getPos();
					map.update();
					
					//draw
					Console.s.clr();
					map.drawMap(Console.s);
					//write
					Console.s.println("Current level: "+map.getLvNo());
					Console.s.println("Type ? for basic help.");
					//input
					cmd=Argument.getArgs(Console.s.readLine());
					//respond
					switch (cmd.cmd()){
						case "d":
							int no=1;
							try {
								no=Integer.parseInt(cmd.get(1));
							} catch (Exception e) {
								
							}
							p.setPos(new Point(p.getX()+no,p.getY()));
						break;
						case "a":
							no=1;
							try {
								no=Integer.parseInt(cmd.get(1));
							} catch (Exception e) {
								
							}
							p.setPos(new Point(p.getX()-no,p.getY()));
						break;
						case "w":
							no=1;
							try {
								no=Integer.parseInt(cmd.get(1));
							} catch (Exception e) {
								
							}
							p.setPos(new Point(p.getX(),p.getY()+no));
						break;
						case "s":
							no=1;
							try {
								no=Integer.parseInt(cmd.get(1));
							} catch (Exception e) {
								
							}
							p.setPos(new Point(p.getX(),p.getY()-no));
						break;
						case "exit":
							state=GAME_STATE.exit;
						break;
						case "menu":
							state=GAME_STATE.menu;
							Console.s.setUserNextLineEnabled(false);
						break;
						case "save":
							saveLevels();
						break;
						case "open":
							no=0;
							try {
								no=Integer.parseInt(cmd.get(1));
							} catch (Exception e) {
								
							}
							map.setLevel(no);
							p.setPos(new Point(0,5));//reset player position
						break;
						case "new":
							no=map.levelNo();
							map.newLevel(no);
							map.setLevel(no);
							p.setPos(new Point(0,5));//reset player position
						break;
						case "goal":
							try {
								no=Integer.parseInt(cmd.get(1));
							} catch (Exception e) {
								no=0;
							}
							Map.getCurrentLevel().setCrystalsToWin(no);
						break;
						case "del":
							map.removeSprites(map.spritesAt(p.getPos()));//deletes all sprites at player's current location
						break;
						
						case "p"://place a block
							switch (cmd.get(1)) {
								case "block":
									Map.getCurrentLevel().add(new Block(p.getPos()));
								break;
								case "scroll":
									name="null";
									String contents="null";
									if (!cmd.get(2).equals("")) {
										name=cmd.get(2);
									}
									if (!cmd.get(3).equals("")) {
										contents=cmd.get(3);
									}
									Map.getCurrentLevel().add(new Scroll(name,p.getPos(),contents));
								break;
								case "crystal":
									Map.getCurrentLevel().add(new Crystal(p.getPos()));
								break;
								case "spike":
									Map.getCurrentLevel().add(new Spike(p.getPos()));
								break;
								case "enemy":
									Map.currentLevel.add(new Enemy(p.getPos()));
							}
						break;
						
						case "time":
							map.toggleEditMode();
						break;
						case "music":
							toggleMusic();
						break;
						case "?":
							Console.s.println("d - move right");
							Console.s.println("a - move left");
							Console.s.println("w - move up");
							Console.s.println("s - move down");
							Console.s.println("open <lvno> - open level <lvno>");
							Console.s.println("new - make a new level");
							Console.s.println("p <sprite> - place a sprite (e.g. 'crystal', 'spike', or 'block'");
							Console.s.println("p scroll <name> <contentents> - place a scroll with specified name and contents");
							Console.s.println("save - saves all levels in their current state");
							Console.s.println("del - delete all sprites under you");
							Console.s.println("goal <no> - set crystals needed for current level");
							Console.s.println("music - toggle music");
							Console.s.println("time - freeze/unfreeze time");
							Console.s.println("menu - return to menu");
							Console.s.println("exit - exit game");
							Console.s.pause();
						break;
					}
				break;
				
			}
		}
		
		System.exit(0);
	}
	
	public void save() {
		String path="saves/"+p.getName()+"/";
		new File("saves").mkdir();
		new File(path).mkdir();
		new File(path+"levels").mkdir();
		//save levels
		map.saveLevels(path+"levels");
		
		BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter(path+"player.dat"));
			//write stuff to player data
			//player properties (name, pos x and y, hp, mhp, jump, max jump, jump horizontal velocity, jumping)
			writer.write(p.getName());//ln 0
			writer.newLine();
			writer.write(String.valueOf(p.getX()));//ln 1
			writer.newLine();
			writer.write(String.valueOf(p.getY()));//ln 2
			writer.newLine();
			writer.write(String.valueOf(p.getHealth()));//ln 3
			writer.newLine();
			writer.write(String.valueOf(p.getMaxHealth()));//ln 4
			writer.newLine();
			writer.write(String.valueOf(p.getJump()));//ln 5
			writer.newLine();
			writer.write(String.valueOf(p.getMaxJump()));//ln 6
			writer.newLine();
			writer.write(String.valueOf(p.getJumpHorizontalVelocity()));//ln 7
			writer.newLine();
			writer.write(String.valueOf(p.isJumping()));//ln 8
			writer.newLine();
			writer.write(String.valueOf(map.getLvNo()));//ln 9 - lv that the player is in
			writer.newLine();
			writer.write(String.valueOf(Crystal.crystalsUsed()));//ln 10 - no of crystals used
			writer.newLine();
			//player inventory
			for (Item i:p.getInventory().getItems()) {
				writer.write("I "+i.getClass().getName()+" "+i.getProps());
				writer.newLine();
			}
			writer.write("|i|");
	        writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
			
        
	}
	
	public void saveLevels() {
		String path="levels/";
		new File(path).mkdir();
		//save levels
		map.saveLevels(path);
	}
	
	public void toggleMusic() {
		music=!music;
		if (!music) {
			sound.pauseSound("Track.wav");
		} else {
			sound.resumeSound("Track.wav");
		}
	}

}
