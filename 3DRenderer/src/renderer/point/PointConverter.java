package renderer.point;

import java.awt.Point;

import renderer.Display;

public class PointConverter {
   
	private static double scale = 4;
	private static final double ZoomFactor = 1.2;
	
	public static void zoomIn() {
		scale *= ZoomFactor;
	}
	
	public static void zoomOut() {
		scale /= ZoomFactor;
	}
    public static Point convertPoint(MyPoint point3D) {
    	double x3d = point3D.getAdjustedY() * scale;
    	double y3d = point3D.getAdjustedZ() * scale;
    	double depth = point3D.getAdjustedX() * scale;
    	double[] newVals = scale(x3d,y3d,depth);
	    int x2d = (int)(Display.WIDTH/2 + newVals[0]);
	    int y2d = (int)(Display.HEIGHT/2 - newVals[1]);
	
	Point point2D = new Point(x2d,y2d);
	return point2D;
}
    
    private static double[] scale(double x3d,double y3d,double depth) {
    	double dist = Math.sqrt(x3d*x3d + y3d*y3d);
    	double theta = Math.atan2(y3d, x3d);
    	double depth2 = 15 - depth;
    	double localScale = Math.abs(640/(depth2+640));
        dist *= localScale;
    	double[] newVals = new double[2];
    	newVals[0] = dist * Math.cos(theta);
    	newVals[1] = dist * Math.sin(theta); 
    	return newVals;	
    }
    
    public static void rotationAxisX(MyPoint p, boolean CW, double degrees) {
    	double radius = Math.sqrt(p.y*p.y + p.z*p.z);
    	double theta = Math.atan2(p.z, p.y);
    	theta += 2*Math.PI/360*degrees*(CW?-1:1);
    	p.y = radius * Math.cos(theta);
    	p.z = radius * Math.sin(theta);
    }
    public static void rotationAxisY(MyPoint p, boolean CW, double degrees) {
    	double radius = Math.sqrt(p.x*p.x + p.z*p.z);
    	double theta = Math.atan2(p.x, p.z);
    	theta += 2*Math.PI/360*degrees*(CW?-1:1);
    	p.x = radius * Math.sin(theta);
    	p.z = radius * Math.cos(theta);
    }
    public static void rotationAxisZ(MyPoint p, boolean CW, double degrees) {
    	double radius = Math.sqrt(p.y*p.y + p.x*p.x);
    	double theta = Math.atan2(p.y, p.x);
    	theta += 2*Math.PI/360*degrees*(CW?-1:1);
    	p.y = radius * Math.sin(theta);
    	p.x = radius * Math.cos(theta);
    }
    
}
