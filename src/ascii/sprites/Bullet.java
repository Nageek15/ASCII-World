package ascii.sprites;

import java.awt.Point;

import ascii.App;
import gameutil.geom.g2D.PointR2;
import gameutil.geom.g2D.VectorR2;

public class Bullet extends Sprite {
	VectorR2 velocity;
	int damage=1;
	public Bullet(Point pos,VectorR2 velocity) {
		super('-', pos, false);
		// TODO Auto-generated constructor stub
		this.velocity=velocity;
		space=true;
	}
	
	public Bullet(Point pos,VectorR2 velocity,int damage) {
		super('-', pos, false);
		// TODO Auto-generated constructor stub
		this.velocity=velocity;
		this.damage=damage;
	}
	
	public void update() {
		PointR2 newPos=new PointR2(pos.getX(),pos.getY());
		newPos.move(velocity);
		pos.setLocation(newPos.getX(),newPos.getY());
		Sprite[] sprites=App.map.spritesAt(pos);
		if (sprites.length>1) {
			for (Sprite s:sprites){
				s.damage(damage);
			}
			if (App.map.solidAt(pos)) {
				inWorld=false;
			}
		}
		
	}
	
	public String getProps() {
		return "|/o\\| java.awt.Point int "+pos.getX()+" int "+pos.getY()+" |/e1\\| gameutil.geom.g2D.VectorR2 double "+velocity.getMagnetudeX()+" double "+velocity.getMagnetudeY()+" |/e\\| int "+damage;
	}
	
	public void deflect() {
		velocity. $X$ (-1);//invert vector components
	}
	
}
