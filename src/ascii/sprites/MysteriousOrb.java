package ascii.sprites;

import java.awt.Point;

import ascii.App;
import gameutil.text.Console;

public class MysteriousOrb extends Item{
	public MysteriousOrb(Point pos) {
		super("Mysterious Orb",'O',pos,"The smooth glass orb glows a various colors. Its colorful surface looks similar oil.",true,true,true);
		space=true;
		worth=50;
	}
	
	public MysteriousOrb() {
		super("Mysterious Orb",'O',new Point(0,0),"The smooth glass orb glows a various colors. Its colorful surface looks similar oil.",true,true,true);
		space=true;
		inWorld=false;
		worth=50;
	}
	
	public void use() {
		int improvement=App.rand.nextInt(4+App.getDifficulty());
		Console.s.print("You aborb energy from the orb...");
		switch (improvement) {
			case 0:
				Console.s.print("Your legs feel considerably stronger.");
				Console.s.println("The orb hops away through the air...");
				//max jump
				App.p.setMaxJump(App.p.getMaxJump()+1);
			break;
			case 1:
				Console.s.print("You feel tougher.");
				Console.s.println("The orb discintegrates...");
				//mhp
				App.p.setMaxHealth(App.p.getMaxHealth()+1+App.rand.nextInt(App.maxDifficulty-App.getDifficulty()));
			break;
			case 2:
				Console.s.println("Your ears perk up as energy flows into them.");
				Console.s.println("The orb pops out of existence...");
				//sound
				App.p.setLowestAudibleVolume(App.p.getLowestAudibleVolume()-.001);
			break;
			case 3:
				Console.s.println("You feel a new found power surging through you.");
				Console.s.println("The orb melds into you...");
				//Atk
				App.p.setAtk(App.p.getAtk()+1);
			break;
			case 4:
				//less hearing
				Console.s.println("The orb flashes brightly, making a deafening boom.");
				Console.s.println("The orb fades away...");
				App.p.setLowestAudibleVolume(App.p.getLowestAudibleVolume()+(.001*(App.rand.nextInt(App.getDifficulty()+1))));
			break;
			case 5:
				Console.s.println("The orb turns a deep crimson color. It gives you power in exchange for your life force.");
				Console.s.println("The now bloated orb floats away...");
				//damage for power
				int amount=App.rand.nextInt(App.getDifficulty())+1;
				App.p.damage(amount);
				App.p.setAtk(App.p.getAtk()+(int) Math.ceil(amount/2));
			break;
			case 6:
				//add letters
				Console.s.println("You hear the jingle of letters in your letter pouch.");
				amount=(App.rand.nextInt(App.getDifficulty())+1)*100;
				App.p.setLetters(App.p.getLetters()+amount);
			break;
			case 7:
				//less mhp
				Console.s.println("The orb turns a dark purple. It overpowers your soul and sucks out a portion of it.");
				Console.s.println("The orb flies away... cackeling...");
				App.p.setMaxHealth(App.p.getMaxHealth()-1-App.rand.nextInt(App.getDifficulty()));
			break;
			case 8:
				//orb split
				Console.s.println("The orb turns red and absorbs some of your life force in order to split in two");
				amount=App.rand.nextInt(App.getDifficulty())+1;
				App.p.damage(amount);
				App.p.getInventory().add(new MysteriousOrb());
				App.p.getInventory().add(new MysteriousOrb());
			break;
			default:
				Console.s.println("The orb withers away like a plant, turning to dust.");
				App.p.getInventory().add(new Dust());
			break;
		}
		Console.s.pause();
	}
	
	@Override
	public String getProps() {
		if (inWorld) {
			return "|/o\\| java.awt.Point |/p\\| int "+getX()+" int "+getY()+" |/e1\\|";//save in world
		} else {
			return "";//save in inventory
		}
	}
}
