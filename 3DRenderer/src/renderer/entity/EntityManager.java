package renderer.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import renderer.entity.builder.BasicEntityBuilder;
import renderer.entity.builder.ComplexEntityBuilder;
import renderer.input.ClickType;
import renderer.input.Mouse;
import renderer.point.MyPoint;
import renderer.point.PointConverter;
import renderer.shapes.MyPolygon;
import renderer.shapes.Tetrahedron;

public class EntityManager {
	
	
	private List<IEntity> entities;
	private int initialX,initialY;
	private double mouseSensitivity = 2.5;
	public EntityManager() {
		
		this.entities = new ArrayList<IEntity>();
	}
	
	public void init() {
		
		//this.entities.add(BasicEntityBuilder.createDiamond(new Color(105, 243, 63), 100, 0, 0, 0));
		//this.entities.add(BasicEntityBuilder.createCube(100, 0, 0, 0);
	    this.entities.add(ComplexEntityBuilder.createRubiksCube(100, 0, 0, 0));
		
	}
	
	public void update(Mouse mouse) {
		int x = mouse.getX();
		int y = mouse.getY();
		if(mouse.getB() == ClickType.LeftClick) {
	        int xDif = x - initialX;
			int yDif = y - initialY;
			this.rotate(true, 0, -yDif/mouseSensitivity , -xDif/mouseSensitivity);
			
		}
		else if(mouse.getB() == ClickType.RightClick) {
	        int xDif = x - initialX;
			this.rotate(true, -xDif/mouseSensitivity, 0 , 0);
			
		}
		
		if(mouse.isScrollUp()) {
			PointConverter.zoomIn();
		}
		else if(mouse.isScrollDown()) {
			PointConverter.zoomOut();
		}
		
		mouse.resetScroll();
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
			entity.rotate(direction, xAngle, yAngle, zAngle);	
		}
		
	}
	
	

}
