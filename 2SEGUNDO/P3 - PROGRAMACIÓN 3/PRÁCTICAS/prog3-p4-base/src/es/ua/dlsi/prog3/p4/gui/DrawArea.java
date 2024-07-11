/**
 * 
 */
package es.ua.dlsi.prog3.p4.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

import es.ua.dlsi.prog3.p4.model.Circle;
import es.ua.dlsi.prog3.p4.model.Coordinate;
import es.ua.dlsi.prog3.p4.model.Form2D;
import es.ua.dlsi.prog3.p4.model.Rectangle;
import es.ua.dlsi.prog3.p4.model.Square;
 
/**
* Component for drawing !
*
* @author sylsau
* 
* (See http://www.ssaurel.com/blog/learn-how-to-make-a-swing-painting-and-drawing-application/)
*
*/
public class DrawArea extends JComponent {
 
  // Image in which we're going to draw
  private Image image;
  // Graphics2D object ==> used to draw on
  private Graphics2D g2;
  // Mouse coordinates
  private int currentX, currentY, oldX, oldY;
  
  boolean circlesFilled=false;
  boolean rectsFilled=false;
 
  List<Form2D> figures;
  Class<?> selectedFigure;
  
  public DrawArea() {
	  figures = new ArrayList<>();
	  selectedFigure = Circle.class;

	  setDoubleBuffered(false);


	  addMouseListener(new MouseAdapter() {
		  public void mousePressed(MouseEvent e) {
			  // save coord x,y when mouse is pressed
			  oldX = e.getX();
			  oldY = e.getY();
		  }
	  });

	  addMouseListener(new MouseAdapter() {
		  public void mouseReleased(MouseEvent e) {
			  currentX = e.getX();
			  currentY = e.getY();
			  // create new Form2D
			  Form2D form = Form2DFactory.createForm2D(selectedFigure, new Coordinate(oldX, oldY), new Coordinate(currentX, currentY)); 
			  figures.add(form);
			  // draw the figure
			  g2.draw(Form2DFactory.createShape(form));
			  // refresh draw area to repaint
			  repaint();
		  }
	  });
	  /*
    addMouseMotionListener(new MouseMotionAdapter() {
      public void mouseDragged(MouseEvent e) {
        // coord x,y when drag mouse
        currentX = e.getX();
        currentY = e.getY();

        if (g2 != null) {
          // draw the selected Figure
        	g2.drawLine(oldX, oldY, currentX, currentY);
          // refresh draw area to repaint
          repaint();
          // store current coords x,y as olds x,y
          oldX = currentX;
          oldY = currentY;
        }
      }
    });
	   */
  }
 
  protected void paintComponent(Graphics g) {
    if (image == null) {
      // image to draw null ==> we create
      image = createImage(getSize().width, getSize().height);
      g2 = (Graphics2D) image.getGraphics();
      // enable antialiasing
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      g2.setPaint(Color.black);
      g2.setStroke(new BasicStroke(3));
      // clear draw area
      clear();
    }
 
    g.drawImage(image, 0, 0, null);
  }
 
  // now we create exposed methods
  public void clear() {
		 figures.clear();
    g2.setPaint(Color.white);
    // draw white on entire draw area to clear
    g2.fillRect(0, 0, getSize().width, getSize().height);
    g2.setPaint(Color.black);
    repaint();
  }
/* 
  public void red() {
    // apply red color on g2 context
    g2.setPaint(Color.red);
  }
*/ 
  public void drawCircle() {
//    g2.setPaint(Color.black);
	  selectedFigure=Circle.class;
  }
/* 
  public void magenta() {
    g2.setPaint(Color.magenta);
  }
*/ 
  public void drawSquare() {
	  selectedFigure=Square.class;
  }
 
  /* 
  public void magenta() {
    g2.setPaint(Color.magenta);
  }
*/ 
  public void drawRectangle() {
	  selectedFigure=Rectangle.class;
  }
  
  /* 
  public void magenta() {
    g2.setPaint(Color.magenta);
  }
*/ 
  static class Form2DFactory {
	  
	  /* 
	  public void magenta() {
	    g2.setPaint(Color.magenta);
	  }
	*/ 
		public static Form2D createForm2D(Class<?> c, Coordinate origin, Coordinate destination)  {
			Form2D d;
			Object[] objects=null;
			
			// I need a switch... >:-(
			if (c == Circle.class) {
				// Compute circle radius
				Coordinate r = destination.subtract(origin);
				double xr = r.getX();
				double yr = r.getY();
				double radius = Math.sqrt( xr*xr + yr*yr );
				objects = new Object[] { origin, radius };
			} else if (c == Rectangle.class) {
				// Compute length and width
				double l = Math.abs(destination.getX() - origin.getX());
				double w = Math.abs(destination.getY() - origin.getY());
				Coordinate position = Form2DFactory.computeRectanglePosition(origin, destination);
				objects = new Object[] { position, 0.0, l, w };
				
			} else if (c == Square.class) {
				// Compute position and side length
				double side = Math.min(Math.abs(destination.getY()-origin.getY()), Math.abs(destination.getX()-origin.getX()));
				double l = destination.getX() - origin.getX();
				double w = destination.getY() - origin.getY();
				Coordinate position = Form2DFactory.computeSquarePosition(origin, destination);
				objects = new Object[] { position, 0.0, side };
			}
			try {
				if (objects==null)
					d = (Form2D) c.newInstance();
				else {
					// objects contains ctor arguments
					Class<?>[] paramTypes = new Class<?>[objects.length];				
					for (int i=0; i < objects.length; i++) {
						paramTypes[i] = objects[i].getClass() == Double.class ? double.class : objects[i].getClass();
					}
					Constructor<?> ctor = c.getConstructor(paramTypes);
					d = (Form2D) ctor.newInstance(objects);
				}
				return d;
			} catch (ClassCastException e) {
				throw new IllegalArgumentException("Unknown form: "+c.getSimpleName(), e); 
			} catch (InstantiationException | IllegalAccessException | NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException e) {
				throw new IllegalArgumentException("Can not instantiate form: "+ c.getSimpleName(),e);
			}
		}
		
		 /* 
		  public void magenta() {
		    g2.setPaint(Color.magenta);
		  }
		*/ 
		private static Coordinate computeRectanglePosition(Coordinate origin, Coordinate destination) {
			Coordinate position;
			Coordinate aux = destination.subtract(origin);
			if (aux.getX() >= 0) {
				if (aux.getY()>=0)
					position = origin;
				else
					position = new Coordinate(origin.getX(),destination.getY());
			} else {
				if (aux.getY()>=0)
					position = new Coordinate(destination.getX(), origin.getY());
				else
					position = destination;
			}
			return position;
		}
		
		 /* 
		  public void magenta() {
		    g2.setPaint(Color.magenta);
		  }
		*/ 
		private static Coordinate computeSquarePosition(Coordinate origin, Coordinate destination) {
			double side = Math.min(Math.abs(destination.getY()-origin.getY()), Math.abs(destination.getX()-origin.getX()));
			Coordinate position;
			Coordinate aux = destination.subtract(origin);
			if (aux.getX() >= 0) {
				if (aux.getY()>=0)
					position = origin;
				else
					position = new Coordinate(origin.getX(),origin.getY()-side);
			} else {
				if (aux.getY()>=0)
					position = new Coordinate(origin.getX()-side, origin.getY());
				else
					position = new Coordinate(origin.getX()-side, origin.getY()-side);
			}
			return position;
		}
		
		 /* 
		  public void magenta() {
		    g2.setPaint(Color.magenta);
		  }
		*/ 
		private static Shape createShape(Form2D form) {
			Shape shape=null;
		
			if (form instanceof Circle) {
				Circle circle = (Circle)form;
				double boundingBoxOrigin_x = form.getPosition().getX()-circle.getRadius();
				double boundingBoxOrigin_y = form.getPosition().getY()-circle.getRadius();
				shape = new Ellipse2D.Double(boundingBoxOrigin_x, boundingBoxOrigin_y, circle.getRadius()*2, circle.getRadius()*2);
			}
			if (form instanceof Rectangle) {
				Rectangle rectangle = (Rectangle)form;
				shape = new Rectangle2D.Double(form.getPosition().getX(), form.getPosition().getY(), rectangle.getLength(), rectangle.getWidth());
			}
			if (form instanceof Square) {
				Square square = (Square)form;
				shape = new Rectangle2D.Double(form.getPosition().getX(), form.getPosition().getY(), square.getSide(), square.getSide());
			}
			
			return shape;
		}

	}

  /* 
  public void magenta() {
    g2.setPaint(Color.magenta);
  }
*/ 
  public void fillCircles() {
	  Shape shape;
	  for (Form2D form : figures) {
		  if (form instanceof Circle) {
			  shape = Form2DFactory.createShape(form);
			  g2.setPaint(Color.black);
			  g2.draw(shape);
			  g2.setPaint(circlesFilled ? Color.white : Color.red);
			  g2.fill(shape);
		  }
	  }
	  circlesFilled = !circlesFilled;
	  g2.setPaint(Color.black);
	  repaint();
  }

  /* 
  public void magenta() {
    g2.setPaint(Color.magenta);
  }
*/ 
  public void fillRects() {
	  Shape shape;
	  for (Form2D form : figures) {
		  if (form instanceof Rectangle || form instanceof Square) {
			  shape = Form2DFactory.createShape(form);
			  g2.setPaint(Color.black);
			  g2.draw(shape);
			  g2.setPaint(rectsFilled ? Color.white : Color.orange);
			  g2.fill(shape);
		  }
	  }
	  rectsFilled = !rectsFilled;
	  g2.setPaint(Color.black);
	  repaint();  }

}
