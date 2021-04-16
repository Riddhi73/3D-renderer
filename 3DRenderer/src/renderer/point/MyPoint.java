package renderer.point;

public class MyPoint {
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

}
