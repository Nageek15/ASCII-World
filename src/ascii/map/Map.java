package ascii.map;

import ascii.App;
import ascii.sprites.Sprite;
import gameutil.text.Console;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Hashtable;

/**Class to handle the mapping of the levels
 *
 */
public class Map {

    ArrayList<Level> levels;
    //need to make these lists into hashtables for layers.
    private ArrayList<Sprite> screenSprites;
    public static Level currentLevel;
    private static Camera camera;
    Point lastCameraLocation;
    JPanel observer;

    public Map(JPanel observer){
        levels=new ArrayList<>();
        levels.add(new Level());
        this.observer=observer;
        camera=new Camera(observer);
        lastCameraLocation=camera.location;
        setLevel(0);
        screenSprites=getSpritesOnScreen();
    }

    public Map(JPanel observer,ArrayList<Level> levels){
        this.levels=levels;
        this.observer=observer;
        camera=new Camera(observer);
        lastCameraLocation=camera.location;
        screenSprites=getSpritesOnScreen();
        setLevel(0);
    }
    
    public Map(){
        levels=new ArrayList<>();
        levels.add(new Level());
        this.observer=null;
        camera=new Camera();
        lastCameraLocation=camera.location;
        setLevel(0);
        screenSprites=getSpritesOnScreen();
    }

    public Map(ArrayList<Level> levels){
        this.levels=levels;
        this.observer=null;
        camera=new Camera();
        lastCameraLocation=camera.location;
        screenSprites=getSpritesOnScreen();
        setLevel(0);
    }

    public void update(){
        //update the current level
        currentLevel.update();
        camera.update();
        screenSprites=getSpritesOnScreen();
        System.out.println(screenSprites.size());
    }

    public static Level getCurrentLevel(){
        return currentLevel;
    }

    public static Camera getCamera(){
        return camera;
    }
    
    public void drawMap(Console c) {
    	//x of point is column, y of point is row
    	Hashtable<Point,Sprite> spritesToDraw=new Hashtable<>();
    	
    	int cameraX=camera.getArea().width/2;
    	int cameraY=camera.getArea().height/2;
    	System.out.println(screenSprites.size());
    	for (Sprite s:screenSprites) {
    		int x=s.getPos().x-camera.location.x+cameraX;
    		int y=s.getPos().y-camera.location.y+cameraY;
    		spritesToDraw.put(new Point(x,y),s);
    	}
    	
    	spritesToDraw.put(new Point(cameraX,cameraY),App.p);
    	
    	for (int row=camera.getArea().height-1;row>=0;row--) {
    		for (int column=0;column<camera.getArea().width;column++) {
    			if (spritesToDraw.containsKey(new Point(column,row))) {
    				Console.s.print(spritesToDraw.get(new Point(column,row)).getChar());
    			}else {
    				Console.s.print("░");
    			}
    		}
    		Console.s.println("");
    	}
    }

    /**Set the level by number
     *
     * @param levelNo
     */
    public void setLevel(int levelNo){
        currentLevel=levels.get(levelNo);
    }

    /**Returns an array of all the sprites visible on the screen
     *
     * @return
     */
    private ArrayList<Sprite> getSpritesOnScreen(){
        ArrayList<Sprite> screenSprites=new ArrayList<Sprite>();
        for (Sprite s:currentLevel.getSprites()){
            if (camera.getArea().contains(s.getPos())) {
                if(screenSprites.add(s)) {
                	System.out.println("Sprite added");
                }
            }
        }
        return screenSprites;
    }
    
    public ArrayList<Sprite> getScreenSprites(){
    	return screenSprites;
    }
    
    public boolean spriteAt(Point pos) {
    	if (camera.getArea().contains(pos)) {
    		for (Sprite s:screenSprites) {
    			if (s.getPos().distance(pos)==0) {
    				return true;
    			}
    		}
    	} else {
    		for (Sprite s:currentLevel.getSprites()) {
    			if (s.getPos().distance(pos)==0) {
    				return true;
    			}
    		}
    	}
    	return false;
    }
    
    public boolean solidAt(Point pos) {
    	if (camera.getArea().contains(pos)) {
    		for (Sprite s:screenSprites) {
    			if (s.getPos().distance(pos)==0&&s.isSolid()) {
    				return true;
    			}
    		}
    	} else {
    		for (Sprite s:currentLevel.getSprites()) {
    			if (s.getPos().distance(pos)==0&&s.isSolid()) {
    				return true;
    			}
    		}
    	}
    	return false;
    }
    
}