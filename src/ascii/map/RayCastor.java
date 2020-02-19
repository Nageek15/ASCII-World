package ascii.map;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

import ascii.sprites.Sprite;
import gameutil.geom.g2D.PointR2;
import gameutil.geom.g2D.RectangleR2;
import gameutil.geom.g2D.VectorR2;

public class RayCastor {
	int resolution;
	VectorR2[] rays;
	int direction=1;//right is 1, left is -1
	static final Random rand=new Random();
	
	private static final VectorR2 invertX=findInvertX();
	
	
	public RayCastor(int resolution) {
		this.resolution=resolution;
		int distance;
		if (resolution%2==0) {
			distance=resolution/2;
		} else {
			distance=(resolution+1)/2;
		}
		rays=new VectorR2[resolution];
		
		for (int i=0;i<resolution;i++) {
			try {
				rays[i]=new VectorR2(distance,i);
				rays[i]. $X$ (10);//make view distance longer
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				//should never happen
			}
		}
	}
	
	private static VectorR2 findInvertX() {
		try {
			return new VectorR2(-1,1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;//should never happen
		}
	}
	
	
	
	public int getResolution() {
		return resolution;
	}
	
	public void turn() {
		for (VectorR2 v:rays) {
			direction*=-1;
			v. $X$ (invertX);//invert X of vectors (multiplying by scalar -1 would result in one direction being perceived upside down
		}
	}
	
	/**Returns the array of sprites that are visible from the array of sprites in the level
	 * 
	 * @param sprites
	 * @return
	 */
	public Sprite[] getVisable(Sprite[] sprites,Point pos) {
		Sprite[] spritesVisable=new Sprite[resolution];
		int visableIndex=0;
		for (int i=0;i<rays.length;i++) {
			VectorR2 v=rays[i];
			try {
				//set position of vector
				/*v.$A$(new VectorR2(pos.x+v.getMagnetudeX(),pos.y+v.getMagnetudeY()));
				v.base().move(pos.x, pos.y);
				*/
				v=new VectorR2(new PointR2(pos.x,pos.y/*-(double)resolution/2*/),new PointR2(pos.x+v.getMagnetudeX(),pos.y/*-(double)resolution/2*/+v.getMagnetudeY()));
			} catch (Exception e) {
				e.printStackTrace();
				//should never happen
			}
			//ArrayList<Sprite> spritesintersecting=new ArrayList<>();
			ArrayList<Sprite> spritesClosest=new ArrayList<>();
			double closest=2147483647;
			//find sprites intersecting ray and the closest to the origin
			for (Sprite s:sprites) {
				RectangleR2 spriteRect=new RectangleR2(new PointR2(s.getPos()),1,1);
				if (v.intersects(spriteRect)) {
					//spritesintersecting.add(s);
					
					double spriteDistance=v.base().distance(v.intersection(spriteRect));//new PointR2(s.getPos()));
					if (spriteDistance<closest) {
						closest=spriteDistance;
						spritesClosest.clear();
						spritesClosest.add(s);
					} else if (spriteDistance==closest) {
						spritesClosest.add(s);
					}
					System.out.println("Closest"+closest);
				}
			}
			
			if (spritesClosest.size()>0) {
				//if any sprites share the closest space psuedorandomly decide what sprite to show
				int index=rand.nextInt(spritesClosest.size());
				spritesVisable[visableIndex]=spritesClosest.get(index);
				System.out.println("Sprites exist");
			} else {
				spritesVisable[visableIndex]=null;//air
			}
			visableIndex++;
			try {
				v=new VectorR2(v.getMagnetudeX()*direction,v.getMagnetudeY()*direction);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return spritesVisable;
	}
	
	
}
