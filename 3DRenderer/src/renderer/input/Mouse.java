package renderer.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class Mouse implements MouseListener, MouseMotionListener, MouseWheelListener {

	private int mouseX = -1;
	private int mouseY = -1;
	private int mouseB = -1;
//	private int scroll = 0;
	
	public int getX() {
		return this.mouseX;
	}
	public int getY() {
		return this.mouseY;
	}
	public ClickType getB() {
		switch(this.mouseB) {
		case 1: 
			return ClickType.LeftClick;
		case 2: 
			return ClickType.ScrollClick;
		case 3: 
			return ClickType.RightClick;
		case 4: 
			return ClickType.BackPage;
		case 5: 
			return ClickType.ForwardPage;
		default:
			return ClickType.UnKnown;
		}
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent event) {
		// TODO Auto-generated method stub
//		System.out.println("Mouse Dragged");
		this.mouseX = event.getX();
		this.mouseY = event.getY();
		
	}

	@Override
	public void mouseMoved(MouseEvent event) {
		// TODO Auto-generated method stub
//		System.out.println("Mouse Moved");
		this.mouseX = event.getX();
		this.mouseY = event.getY();
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		//System.out.println("HELLO");
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		this.mouseB = e.getButton();
	}
	
	public int resetButton() {
		this.mouseB = -1;
		return this.mouseB;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		this.mouseB = -1;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
