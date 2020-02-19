package gameutil.geom.g2D;

import gameutil.geom.Vector;

public class ShapeR2 {
	protected double area;
	protected double perimeter;
	protected PointR2 center;
	
	protected ShapeR2(double area,double perimeter,PointR2 pos) {
		this.area=area;
		this.perimeter=perimeter;
		center=pos;
	}
	
	public PointR2 getCenter() {
		//try {
			return new PointR2(center.getX(),center.getY());
		/*} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}*/
	}
	
	public boolean setPos(PointR2 p) {
		//try {
			center=new PointR2(p.getX(),p.getY());//(PointR2) p.clone();
			return true;
		/*} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}*/
	}
	
	public void move(VectorR2 v) {
		center.move(v);
	}
	
	public void move(Vector v) {
		center.move(v);
	}

}
