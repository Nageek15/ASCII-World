package ascii.sprites;

import java.awt.Point;

import ascii.App;
import gameutil.geom.g2D.PointR2;
import gameutil.geom.g2D.VectorR2;

public class Sound extends Sprite{
	private String sound;
	private double amplitude;
	private final double startingAmplitude;
	private String fileName="";
	private long lifespan=0;
	private VectorR2 velocity;
	
	public Sound(Point pos,String sound,double amplitude,VectorR2 velocity) {
		super(')',pos,false);
		space=true;
		visible=false;
		this.sound=sound;
		this.amplitude=amplitude;
		fileName="lelelele.wav";
		startingAmplitude=amplitude;
		this.velocity=velocity;
	}
	
	public Sound(Point pos,String sound,double amplitude,String name,VectorR2 velocity) {
		super(')',pos,false);
		space=true;
		visible=false;
		this.sound=sound;
		this.amplitude=amplitude;
		fileName=name;
		this.velocity=velocity;
		startingAmplitude=amplitude;
	}
	@Override
	public void update() {
		
		PointR2 newPos=new PointR2(pos);
		newPos.move(velocity);
		
		if (App.map.solidAt(newPos.toPoint())) {
			velocity.$X$(-1);//reflect
		} else if (App.p.getPos().equals(newPos.toPoint())) {
			velocity.$X$(-1);//reflect
			App.p.hear(this);
		}
		
		attemptMove(newPos.toPoint());
		
		lifespan++;
		amplitude=startingAmplitude/Math.pow(lifespan,2);
		if (amplitude<.000000001) {
			inWorld=false;//sound is too soft to be heard so remove it from the world
		}
	}
	
	
	public void play() {
		App.sound.playSound(fileName,1);
	}
	
	public double getAmplitude() {
		return amplitude;
	}
	
	public long getLifespan() {
		return lifespan;
	}
	
	@Override
	public String getProps() {
		return "|/o\\| java.awt.Point |/p\\| int "+getX()+" int "+getY()+" |/e1\\| String |/l\\| "+sound+" |/eS\\| double "+String.valueOf(amplitude)+" String |/l\\| "+fileName+" |/eS\\| |/o\\| gameutil.geom.g2D.VectorR2 int "+velocity.getMagnetudeX()+" int "+velocity.getMagnetudeY()+"|/e1\\|";
	}

}
