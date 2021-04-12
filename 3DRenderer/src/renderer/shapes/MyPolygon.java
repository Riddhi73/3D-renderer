package renderer.shapes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import renderer.point.MyPoint;
import renderer.point.PointConverter;

public class MyPolygon {
	
	private MyPoint[] points;
	private Color color;
	
	public MyPolygon(Color color,MyPoint... points) {
		this.color = color;
		this.points = new MyPoint[points.length];
		for(int i=0;i<points.length;i++) {
			MyPoint p = points[i];
			this.points[i] = new MyPoint(p.x,p.y,p.z);
		}
	}
	

	public MyPolygon(MyPoint... points) {
		this.color = Color.WHITE;
		this.points = new MyPoint[points.length];
		for(int i=0;i<points.length;i++) {
			MyPoint p = points[i];
			this.points[i] = new MyPoint(p.x,p.y,p.z);
		}
	}
	
	
	public void render(Graphics g) {
		Polygon poly = new Polygon();
		for(int i=0;i<this.points.length;i++) {
			Point p = PointConverter.convertPoint(this.points[i]);
			poly.addPoint(p.x, p.y);
		}
		g.setColor(this.color);
		g.fillPolygon(poly);
	}
	
	public void rotate(boolean CW, double xDegrees, double yDegrees, double zDegrees) {
		for(MyPoint p : points) {
			PointConverter.rotationAxisX(p, CW, xDegrees);
			PointConverter.rotationAxisY(p, CW, yDegrees);
     		PointConverter.rotationAxisZ(p, CW, zDegrees);

		}
	}
	public double getAverageX() {
		double sum = 0;
		for(MyPoint p: this.points) {
			sum+=p.x;
		}
		return sum/this.points.length;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	
	public static MyPolygon[] sortPoligons(MyPolygon[] polygons) {
		List<MyPolygon> polygonList = new ArrayList<MyPolygon>();
		for(MyPolygon poly: polygons) {
			polygonList.add(poly);
		}
		
		
		Collections.sort(polygonList,new Comparator<MyPolygon>() {
			@Override
			public int compare(MyPolygon p1, MyPolygon p2) {
				return p2.getAverageX() - p1.getAverageX() < 0? 1: -1;
			}
		});
		
		
		for(int i=0;i<polygons.length;i++) {
			polygons[i] = polygonList.get(i);
		}
		return polygons;
	}

}
