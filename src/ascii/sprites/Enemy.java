package ascii.sprites;

import java.awt.Point;

import ascii.App;

public class Enemy extends Sprite{
	boolean moveleft;
	public Enemy(Point pos) {
		super("Enemy",'#',pos,1);
		moveleft=true;
		solid=true;
	}
	
	public Enemy(Point pos, boolean moveleft) {
		super("Enemy",'#',pos,1);
		this.moveleft=moveleft;
		solid=true;
	}
	
	@Override
	public void update() {
		
		attemptMove(new Point(pos.x,pos.y-1));
		//patrol platform
		if (App.p.getPos().distance(pos)<=1) {
			App.p.damage(1);
		}
		if (moveleft) {
			if (App.map.solidAt(new Point(pos.x-1,pos.y-1))&&!App.map.solidAt(new Point(pos.x-1, pos.y))) {
				attemptMove(new Point(pos.x-1,pos.y));
			} else {
				moveleft=false;
			}
		} else {
			if (App.map.solidAt(new Point(pos.x+1,pos.y-1))&&!App.map.solidAt(new Point(pos.x+1, pos.y))) {
				attemptMove(new Point(pos.x+1,pos.y));
			} else {
				moveleft=true;
			}
		}
		if (hp<=0) {
			inWorld=false;
			return;
		}
	}
	
	public String getProps() {
		return "|/o\\| java.awt.Point |/p\\| int "+pos.x+" int "+pos.y+" |/e1\\| |/p\\| boolean "+String.valueOf(moveleft);
	}
	
}
