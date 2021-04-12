package renderer;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import renderer.input.ClickType;
import renderer.input.Mouse;
import renderer.point.MyPoint;
import renderer.shapes.MyPolygon;
import renderer.shapes.Tetrahedron;

public class Display extends Canvas implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Thread thread;
	private JFrame frame;
	private static String title="3D Renderer";
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	private static boolean running  = false;
	
	private Tetrahedron tetra;
	
	private Mouse mouse;
	
	public Display() {
		this.frame = new JFrame();
		
		Dimension size = new Dimension(WIDTH,HEIGHT);
		this.setPreferredSize(size);
		
		this.mouse = new Mouse();
		
		this.addMouseListener(this.mouse);
		this.addMouseMotionListener(this.mouse);
		this.addMouseWheelListener(this.mouse);
		
	}
	
	public static void main(String[] args) {
		Display display = new Display();
		display.frame.setTitle(title);
		display.frame.add(display);
		display.frame.pack();
		display.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		display.frame.setLocationRelativeTo(null);
		display.frame.setResizable(true);
		display.frame.setVisible(true);
		display.start();
	}
	
	public synchronized void start() {
		running = true;
		this.thread = new Thread(this,"Display");
		this.thread.start();
	}
	
    public synchronized void stop() {
    	running = false;
    	try {
			this.thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		long Lasttime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0/60;
		double delta = 0;
		int frames = 0;
		
		init();
		
		while(running) {
			long now = System.nanoTime();
			delta+=(now - Lasttime)/ns;
			Lasttime = now;
			while(delta >= 1) {
				update();
				delta--;
				render();
			    frames++;
			}
			
			if(System.currentTimeMillis() - timer > 1000) {
				timer+=1000;
				this.frame.setTitle(title + " | "+ frames + "fps");
				frames = 0;
			}
		}
		stop();
	}

	private void init() {
		// TODO Auto-generated method stub
		int s = 100;
		MyPoint p1 = new MyPoint(s/2,-s/2,-s/2);
		MyPoint p2 = new MyPoint(s/2,s/2,-s/2);
		MyPoint p3 = new MyPoint(s/2,s/2,s/2);
		MyPoint p4 = new MyPoint(s/2,-s/2,s/2);
		MyPoint p5 = new MyPoint(-s/2,-s/2,-s/2);
		MyPoint p6 = new MyPoint(-s/2,s/2,-s/2);
		MyPoint p7 = new MyPoint(-s/2,s/2,s/2);
		MyPoint p8 = new MyPoint(-s/2,-s/2,s/2);
		
		this.tetra = new Tetrahedron(
				
				new MyPolygon(Color.BLUE, p5,p6,p7,p8),	
				new MyPolygon(Color.WHITE, p1,p2,p6,p5),
				new MyPolygon(Color.YELLOW ,p1,p5,p8,p4),
				new MyPolygon(Color.GREEN, p2,p6,p7,p3),
				new MyPolygon(Color.CYAN, p4,p3,p7,p8),
				new MyPolygon(Color.RED, p1,p2,p3,p4));
		//this.tetra.rotate(true, 40, 0, 0);
	}
	ClickType prevMouse = ClickType.UnKnown;
	int initialX,initialY;
	double mouseSensitivity = 2.5;
	private void update() {
		// TODO Auto-generated method stub	
		int x = this.mouse.getX();
		int y = this.mouse.getY();
		if(this.mouse.getB() == ClickType.LeftClick) {
	        int xDif = x - initialX;
			int yDif = y - initialY;
			this.tetra.rotate(true, 0, -yDif/mouseSensitivity , -xDif/mouseSensitivity);
			
		}
		else if(this.mouse.getB() == ClickType.RightClick) {
	        int xDif = x - initialX;
			this.tetra.rotate(true, -xDif/mouseSensitivity, 0 , 0);
			
		}
		initialX = x;
		initialY = y;
	}

	private void render() {
		// TODO Auto-generated method stub
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		g.setColor(new Color(69,69,69)); 
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		
		tetra.render(g);
		g.dispose();
		bs.show();
	}

}
