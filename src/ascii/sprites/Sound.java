package ascii.sprites;

import java.awt.Point;

import ascii.App;

public class Sound extends Sprite{
	String sound;
	double amplitude;
	String fileName="";
	long lifespan=0;
	
	public Sound(Point pos,String sound,double amplitude) {
		super(')',pos,false);
		space=true;
		visible=false;
		this.sound=sound;
		this.amplitude=amplitude;
		fileName="lelelele.wav";
	}
	
	public Sound(Point pos,String sound,double amplitude,String name) {
		super(')',pos,false);
		space=true;
		visible=false;
		this.sound=sound;
		this.amplitude=amplitude;
		fileName=name;
		
	}
	@Override
	public void update() {
		lifespan++;
	}
	
	
	public void play() {
		App.sound.playSound(fileName,1);
	}
	
	@Override
	public String getProps() {
		return "|/o\\| java.awt.Point |/p\\| int "+getX()+" int "+getY()+" |/e1\\| String |/l\\| "+sound+" |/eS\\| double "+String.valueOf(amplitude)+" String |/l\\| "+fileName+" |/eS\\|";
	}

}
