package ascii;

import java.util.ArrayList;
import java.util.Hashtable;

public final class Dialogue {
	static Hashtable<String, ArrayList<String>> dialogues;
	
	private Dialogue() {
		dialogues=new Hashtable<>();
		ArrayList<String> list;
		//Shop dialogs
		
			//Item not in stock
				dialogues.put("notInStock", new ArrayList<>());
				list=dialogues.get("notInStock");
				list.add("Sorry, that's not in stock at the moment.");
				list.add("I don't have any of that.");
				list.add("We're all cleared out of that item.");
				list.add("Another customer already took the last one.");
			//Not enough Letters
				dialogues.put("notEnoughtL", new ArrayList<>());
				list=dialogues.get("notEnoughL");
				list.add("Sorry, I'll need more letters than that.");
				list.add("That amount won't suffice.");
				list.add("I can't sell this to you if that's all you're offering.");
				list.add("No can do mate.");
			//Greeting
				
	}
	
	public static String getDialogue(String name) {
		ArrayList<String> list=dialogues.get(name);
		int index=App.rand.nextInt(list.size());
		return list.get(index);
	}
	
	
}
