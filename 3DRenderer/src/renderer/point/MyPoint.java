package renderer.point;

public class MyPoint {
	
    public final static MyPoint origin = new MyPoint(0,0,0);
    
    public double x,y,z;
	public double xoffset,yoffset,zoffset;
	public MyPoint(double x,double y,double z) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.xoffset = 0;
		this.yoffset = 0;
		this.zoffset = 0;
	}
	public double getAdjustedX() {
	    return this.x + this.xoffset;
	}
	
	public double getAdjustedY() {
        return this.y + this.yoffset;
    }
	
	public double getAdjustedZ() {
        return this.z + this.zoffset;
    }
	
	public static double dist(MyPoint p1, MyPoint p2) {
	    double x2 = Math.pow(p1.x - p2.x, 2);
	    double y2 = Math.pow(p1.y - p2.y, 2);
	    double z2 = Math.pow(p1.z - p2.z, 2);
	    return Math.sqrt(x2 + y2 + z2);
	}

}
