package ascii.sprites;

import java.util.ArrayList;

import gameutil.text.Console;

public class Inventory {
	private ArrayList<Item> items;
	private Sprite observer;
	
	public Inventory(Sprite observer) {
		items=new ArrayList<>();
		this.observer=observer;
	}
	
	public void add(Item i) {
		items.add(i);
		i.inWorld=false;
	}
	
	public boolean drop(Item i) {
		if (items.contains(i)) {
			items.remove(i);
			i.setPos(observer.getPos());
			return true;
		}
		return false;
	}
	
	public boolean drop(String name) {
		for (Item i:items) {
			if (i.name.equals(name)) {
				return drop(i);
			}
		}
		return false;
	}
	
	public int drop(String name,int amount) {
		int amountDropped=0;
		for (Item i:items) {
			if (amountDropped>=amount) {
				break;
			}
			if (i.name.equals(name)) {
				drop(i);
				amountDropped++;
			}
		}
		
		return amountDropped;
	}
	
	public boolean use(Item i) {
		if (i.useable && items.contains(i)) {
			i.use();
			return true;
		}
		return false;
	}
	
	public int use(String name,int amount) {
		int amountUsed=0;
		ArrayList<Item> itemsToRemove=new ArrayList<Item>();
		for (Item i:items) {
			if (amountUsed>=amount) {
				break;
			}
			if (i.name.equals(name)) {
				if (use(i)) {
					if (i.consumable) {//only remove consumable items
						itemsToRemove.add(i);
					}
					amountUsed++;
				}
			}
		}
		
		items.removeAll(itemsToRemove);
		
		return amountUsed;
	}
	
	public void printContents() {
		Console.s.println("Inventory:");
		Console.s.println("");
		for (Item i:items) {
			Console.s.println("   - ["+i.c+"]"+" "+i.name);
			Console.s.println("       Usable: "+i.useable);
			Console.s.println("       Consumable: "+i.consumable);
			Console.s.println("       ");
			Console.s.println("       "+i.desc);
			Console.s.println("");
		}
	}
	
	public ArrayList<Item> getItems(){
		return items;
	}
}
