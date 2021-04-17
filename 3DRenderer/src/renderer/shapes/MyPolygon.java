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
import renderer.point.MyVector;
import renderer.point.PointConverter;

public class MyPolygon {
	
	private static final double AMBIENT_LIGHTING = 0.05;
    private MyPoint[] points;
	private Color baseColor,lightingColor;
	private boolean visible;
	
	public MyPolygon(Color color,MyPoint... points) {
		this.baseColor = this.lightingColor = color;
		this.points = new MyPoint[points.length];
		
		for(int i=0;i<points.length;i++) {
			MyPoint p = points[i];
			this.points[i] = new MyPoint(p.x,p.y,p.z);
		}
		this.updateVisibility();
	}
	

	public MyPolygon(MyPoint... points) {
		this.baseColor = this.lightingColor =  Color.WHITE;
		this.points = new MyPoint[points.length];
		for(int i=0;i<points.length;i++) {
			MyPoint p = points[i];
			this.points[i] = new MyPoint(p.x,p.y,p.z);
		}
		this.updateVisibility();
	}
	
	
	public void render(Graphics g) {
	    if(!this.visible)
	        return;
	    
	    
		Polygon poly = new Polygon();
		for(int i=0;i<this.points.length;i++) {
			Point p = PointConverter.convertPoint(this.points[i]);
			poly.addPoint(p.x, p.y);
		}
		g.setColor(this.lightingColor);
		g.fillPolygon(poly);
	}
	
	public void translate(double x, double y, double z) {
	    for(MyPoint p : points) {
           p.xoffset+=x;
           p.yoffset+=y;
           p.zoffset+=z;

        }
	    this.updateVisibility();
	}
	
	
	
	
	public void rotate(boolean CW, double xDegrees, double yDegrees, double zDegrees, MyVector lightvector) {
		for(MyPoint p : points) {
			PointConverter.rotationAxisX(p, CW, xDegrees);
			PointConverter.rotationAxisY(p, CW, yDegrees);
     		PointConverter.rotationAxisZ(p, CW, zDegrees);

		}
		this.updateVisibility();
		this.setLighting(lightvector);
	}
	
	
	public double getAverageX() {
		double sum = 0;
		for(MyPoint p: this.points) {
			sum+=p.x + p.xoffset;
		}
		return sum/this.points.length;
	}
	
	
	private MyPoint getAveragePoint() {
	    double x = 0;
	    double y = 0;
	    double z = 0;
	    
	    for(MyPoint p : this.points) {
	        x += p.x + p.xoffset;
	        y += p.y + p.yoffset;
	        z += p.z + p.zoffset;        
	    }
	    
	    x /= this.points.length;
	    y /= this.points.length;
	    z /= this.points.length;
	    
	    return new MyPoint(x,y,z);
	    
	}
	public void setColor(Color color) {
		this.baseColor = color;
	}
	
	public static MyPolygon[] sortPoligons(MyPolygon[] polygons) {
		List<MyPolygon> polygonList = new ArrayList<MyPolygon>();
		for(MyPolygon poly: polygons) {
			polygonList.add(poly);
		}
		
		
		Collections.sort(polygonList,new Comparator<MyPolygon>() {
			@Override
			public int compare(MyPolygon p1, MyPolygon p2) {
			    MyPoint p1Average = p1.getAveragePoint();
			    MyPoint p2Average = p2.getAveragePoint();
			    double p1dist = MyPoint.dist(p1Average,MyPoint.origin);
			    double p2dist = MyPoint.dist(p2Average,MyPoint.origin);
			    double diff = p1dist - p2dist;
			    if(diff == 0)
			        return 0;
			    
				return diff < 0? 1: -1;
			}
		});
		
		
		for(int i=0;i<polygons.length;i++) {
			polygons[i] = polygonList.get(i);
		}
		return polygons;
	}
	
	public void setLighting(MyVector lightVector) {
	    if(this.points.length<3) {
	        return;
	    }
	    
	    MyVector v1 = new MyVector(this.points[0],this.points[1]);
	    MyVector v2 = new MyVector(this.points[1],this.points[2]);
	    MyVector normalVector = MyVector.normalize(MyVector.cross(v2, v1));
	    double dot = MyVector.dot(normalVector, lightVector);
	    double sign = dot<0 ? -1 : 1;
	    dot = sign * dot * dot;
	    dot = (dot + 1)/2 * 0.8;
	    
	    double lightingRatio = Math.min(1, Math.max(0, AMBIENT_LIGHTING + dot));
	    this.updateLightingColor(lightingRatio);
	}
	
	private void updateVisibility() {
	    this.visible = this.getAverageX() < 0; 
	    
	}
	
	public boolean isVisible() {
	    
	    return this.visible;
	    
	}
	
	private void updateLightingColor(double lightRatio) {
	    
	    int red = (int) (this.baseColor.getRed() * lightRatio);
	    int blue = (int) (this.baseColor.getBlue() * lightRatio);
	    int green = (int) (this.baseColor.getGreen() * lightRatio);
	    
	    this.lightingColor = new Color(red, blue, green);
	}
	

}
