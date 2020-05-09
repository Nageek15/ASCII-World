package ascii.sprites;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Hashtable;

import ascii.App;
import ascii.Dialogue;
import ascii.map.Map;
import gameutil.text.Argument;
import gameutil.text.Console;

public class Shop extends Sprite{
	Inventory inv=new Inventory(this);
	int tick=0;
	int stockRate;
	public Shop(Point pos,boolean generateStock) {
		super('S',pos);
		space=true;
		solid=false;
		if (generateStock) {
			generateStock();
		}
		stockRate=20+5*App.getDifficulty();
	}
	
	public void interact() {
		//shop interface
		boolean shopping=true;
		while (shopping) {
			Console.s.clr();
			Console.s.println("Shop:");
			Console.s.println("");
			Hashtable<String,int[]> amounts=new Hashtable<>();
			for (Item i:inv.getItems()) {
				if (amounts.containsKey(i.getName())) {
					amounts.replace(i.getName(), new int[] {amounts.get(i.getName())[0]+1,i.getWorth()});
				} else {
					amounts.put(i.getName(), new int[] {1,i.getWorth()});
				}
				
			}
			for (String name:amounts.keySet()) {
				Console.s.println(amounts.get(name)[1]*2+"L "+name+" x "+amounts.get(name)[0]);
			}
			Console.s.println("");
			Console.s.println("HP: "+App.p.getHealth()+"/"+App.p.getMaxHealth());
			Console.s.println(App.p.getLetters()+"L");
			Console.s.println("Type ? for help.");
			Argument cmd=Argument.getArgs(Console.s.readLine());
			switch (cmd.get(0)) {
				case "?":
					Console.s.println("exit - exit shop interface");
					Console.s.println("buy <item> - buy an item");
					Console.s.println("sell <item> - sell an item");
					Console.s.println("inv - view inventory");
					Console.s.println("");
					Console.s.println("In ASCII world letters or L are used for currency. Upon examination, a letter has the appearance of an L.");
					Console.s.pause();
				break;
				case "exit":
					shopping=false;
				break;
				case "inv":
					App.p.getInventory().printContents();
					Console.s.pause();
				break;
				case "sell":
					String itemName=cmd.get(1);
					//System.out.println("itemName:"+itemName);
					int amount;
					try {
						//attempt to use second arg for amount to use
						amount=Integer.parseInt(cmd.get(2));
					} catch (NumberFormatException e) {
						//use only one
						amount=1;
					}
					ArrayList<Item> items=App.p.getInventory().getItems(itemName, amount);
					App.p.getInventory().remove(items);
					int letters=0;
					for (Item i:items) {
						letters=letters+i.getWorth();
						if (inv.getItems(i.getName()).size()==0) {
							inv.add(i);
						}
					}
					App.p.setLetters(App.p.getLetters()+letters);
					Console.s.println("You sold "+itemName+" x "+items.size()+" for "+letters+"L.");
				break;
				case "buy":
					itemName=cmd.get(1);
					//System.out.println("itemName:"+itemName);
					try {
						//attempt to use second arg for amount to use
						amount=Integer.parseInt(cmd.get(2));
					} catch (NumberFormatException e) {
						//use only one
						amount=1;
					}
					items=inv.getItems(itemName, amount);
					
					letters=0;
					for (Item i:items) {
						letters=letters+i.getWorth();
						if (inv.getItems(i.getName()).size()==0) {
							inv.add(i);
						}
					}
					
					if (items.size()==0) {
						Console.s.println("Shop Keeper: "+Dialogue.getDialogue("notInStock"));
					} else if (letters>App.p.getLetters()) {
						Console.s.println("Shop Keeper: "+Dialogue.getDialogue("notEnoughL"));
					} else {
						Console.s.println("Shop Keeper: Here you are.");
						App.p.setLetters(App.p.getLetters()-letters);
						inv.remove(items);
						App.p.getInventory().add(items);
					}
					
				break;
			}
		}
		
	}
	
	public void update() {
		tick+=App.rand.nextInt(2)+1;
		if (tick>=stockRate) {
			//restock after enough ticks have passed
			tick=0;
			generateStock(App.rand.nextInt(1+(int)Math.floor(App.map.getLvNo()/(1+App.getDifficulty())))+1);
		}
	}
	
	private void generateStock(int items) {
		for (int i=0;i<items;i++) {
			int item=App.rand.nextInt(12);
			switch (item) {
				case 0:
					inv.add(new Dust());
				break;
				case 1:
					inv.add(new OrbOfHealing());
				break;
				case 2:
					inv.add(new ScrollOfStats());
				break;
				case 3:
					inv.add(new MysteriousOrb());
				break;
				case 4:
					inv.add(new OrbOfVitality());
				break;
				case 5:
					inv.add(new Scroll("Scroll of Yelling", "Yelling can be useful when in first person mode. Distance=Time/2 when echolocating solids (eg. blocks).\n\n"
														  + "yell <right/left/up/down> - yell\n"
										 				  + "yell <rightup/leftup/rightdown/leftdown> - yell\n"
										 				  + "yell <ahead/back/aheadup/backdown/aheaddown/backup> - yell"));
				break;
				case 6:
					inv.add(new Teleporter());
				break;
				case 7:
					inv.add(new Dust());
				break;
				case 8:
					inv.add(new OrbOfHealing());
				break;
				case 9:
					inv.add(new MysteriousOrb());
				break;
				case 10:
					inv.add(new MysteriousOrb());
				break;
				case 11:
					inv.add(new OrbOfHealing());
				break;
			}
		}
	}
	
	private void generateStock() {
		generateStock(App.rand.nextInt(15+App.maxDifficulty-App.getDifficulty())+1);
	}
	
	public Inventory getInventory() {
		return inv;
	}
	
	@Override
	public String getProps() {
		if (App.getState()==App.GAME_STATE.edit) {
			return "|/o\\| java.awt.Point |/p\\| int "+String.valueOf(getX())+" int "+String.valueOf(getY())+" |/e1\\| |/p\\| boolean true";//save with generate items set to true
		} else {
			return "|/o\\| java.awt.Point |/p\\| int "+String.valueOf(getX())+" int "+String.valueOf(getY())+" |/e1\\| |/p\\| boolean false";//save with generate items set to false (saving from in game)
		}
		
	}
}
