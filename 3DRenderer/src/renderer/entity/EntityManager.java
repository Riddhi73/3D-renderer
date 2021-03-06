package renderer.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import renderer.entity.builder.BasicEntityBuilder;
import renderer.entity.builder.ComplexEntityBuilder;
import renderer.input.ClickType;
import renderer.input.Keyboard;
import renderer.input.Mouse;
import renderer.input.UserInput;
import renderer.point.MyPoint;
import renderer.point.MyVector;
import renderer.point.PointConverter;
import renderer.shapes.MyPolygon;
import renderer.shapes.Polyhedron;
import renderer.world.Camera;

public class EntityManager {
	
	
	private List<IEntity> entities;
	private int initialX,initialY;
	private double mouseSensitivity = 2.5;
	private double moveSpeed = 10;
	private MyVector lightVector = MyVector.normalize(new MyVector(1,1,1));
	private Mouse mouse;
	private Keyboard keyboard;
	private Camera camera;
	public EntityManager() {
		
		this.entities = new ArrayList<IEntity>();
		this.camera = new Camera(0, 0, 0);
	}
	
	public void init(UserInput userinput) {
		this.mouse = userinput.mouse;
		this.keyboard = userinput.keyboard;
		//this.entities.add(BasicEntityBuilder.createDiamond(new Color(105, 243, 63), 100, 0, 0, 0));
		//this.entities.add(BasicEntityBuilder.createCube(100, 0, 0, 0);
	    this.entities.add(ComplexEntityBuilder.createRubiksCube(100, 0, 0, 0));
	    this.setLighting();
		
	}
	
	private void setLighting() {
        // TODO Auto-generated method stub
        for(IEntity entity: this.entities) {
            entity.setLighting(this.lightVector);
        }
    }

    public void update() {
		int x = this.mouse.getX();
		int y = this.mouse.getY();
		if(this.mouse.getB() == ClickType.LeftClick) {
	        int xDif = x - initialX;
			int yDif = y - initialY;
			this.rotate(true, 0, -yDif/mouseSensitivity , -xDif/mouseSensitivity);
			
		}
		else if(this.mouse.getB() == ClickType.RightClick) {
	        int xDif = x - initialX;
			this.rotate(true, -xDif/mouseSensitivity, 0 , 0);
			
		}
		
		if(this.mouse.isScrollUp()) {
			PointConverter.zoomIn();
		}
		else if(this.mouse.isScrollDown()) {
			PointConverter.zoomOut();
		}
		
		if(this.keyboard.getLeft()) {
		    this.camera.translate(0, -moveSpeed, 0);
		    //System.out.println("HELLO");
		    for(IEntity entity: this.entities) {
		        entity.translate(0, moveSpeed, 0);
		    }
		}
		if(this.keyboard.getRight()) {
            this.camera.translate(0, moveSpeed, 0);
            for(IEntity entity: this.entities) {
                entity.translate(0, -moveSpeed, 0);
            }
        }
		if(this.keyboard.getUp()) {
            this.camera.translate(0,0, moveSpeed);
            for(IEntity entity: this.entities) {
                entity.translate(0,0, -moveSpeed);
            }
        }
		if(this.keyboard.getDown()) {
            this.camera.translate(0,0, -moveSpeed);
            for(IEntity entity: this.entities) {
                entity.translate(0,0, moveSpeed);
            }
        }
		if(this.keyboard.getForward()) {
            this.camera.translate(-moveSpeed,0, 0);
            for(IEntity entity: this.entities) {
                entity.translate(moveSpeed,0, 0);
            }
        }
		if(this.keyboard.getBackward()) {
            this.camera.translate(moveSpeed,0, 0);
            for(IEntity entity: this.entities) {
                entity.translate(-moveSpeed,0, 0);
            }
        }
		this.mouse.resetScroll();
		this.keyboard.update();
		initialX = x;
		initialY = y;
		
	}
	public void render(Graphics g) {
		
		for(IEntity entity: this.entities) {
			entity.render(g);	
		}
	}
	
	private void rotate(boolean direction, double xAngle, double yAngle, double zAngle) {
		for(IEntity entity: this.entities) {
			entity.rotate(direction, xAngle, yAngle, zAngle, this.lightVector);	
		}
		
	}
	
	

}
