package es.ua.dlsi.prog3.p4.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
 
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
 
/** This is a tiny 2D drawing application that allos you to
 * - draw circles, rectangles or squares
 * - fill/unfill all the circles with the same color
 * - fill/unfill al the rectangles and squares with the same color
 * 
 * @author pierre
 * Based on
 * (See http://www.ssaurel.com/blog/learn-how-to-make-a-swing-painting-and-drawing-application/)
 */
public class SwingPaint {
 
  JButton clearBtn, circleBtn, rectangleBtn, squareBtn;
  JToggleButton fillCircleBtn, fillRectsBtn;
  
  DrawArea drawArea;
  ActionListener actionListener = new ActionListener() {
 
  public void actionPerformed(ActionEvent e) {
      if (e.getSource() == clearBtn) {
        drawArea.clear();
      } else if (e.getSource() == circleBtn) {
        drawArea.drawCircle();
      } else if (e.getSource() == rectangleBtn) {
        drawArea.drawRectangle();
      } else if (e.getSource() == squareBtn) {
        drawArea.drawSquare();
      } else if (e.getSource() == fillCircleBtn) {
          drawArea.fillCircles();
        } else if (e.getSource() == fillRectsBtn) {
            drawArea.fillRects();
        } 
    }
  };
 
  public static void main(String[] args) {
    new SwingPaint().show();
  }
 
  public void show() {
    // create main frame
    JFrame frame = new JFrame("Swing Paint");
    Container content = frame.getContentPane();
    // set layout on content pane
    content.setLayout(new BorderLayout());
    // create draw area
    drawArea = new DrawArea();
 
    // add to content pane
    content.add(drawArea, BorderLayout.CENTER);
 
    // create controls to apply colors and call clear feature
    JPanel controls = new JPanel();
 
    clearBtn = new JButton("Clear");
    clearBtn.addActionListener(actionListener);
    circleBtn = new JButton("Circle");
    circleBtn.addActionListener(actionListener);
    rectangleBtn = new JButton("Rectangle");
    rectangleBtn.addActionListener(actionListener);
    squareBtn = new JButton("Square");
    squareBtn.addActionListener(actionListener);
    fillCircleBtn = new JToggleButton("Fill circles",false);
    fillCircleBtn.addActionListener(actionListener);
    fillRectsBtn = new JToggleButton("Fill Rects & Sq.",false);
    fillRectsBtn.addActionListener(actionListener);
 
    // add to panel
    controls.add(squareBtn);
    controls.add(rectangleBtn);
    controls.add(circleBtn);
    controls.add(clearBtn);
    controls.add(fillCircleBtn);
    controls.add(fillRectsBtn);
 
    // add to content pane
    content.add(controls, BorderLayout.NORTH);
 
    frame.setSize(800, 800);
    // can close frame
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    // show the swing paint result
    frame.setVisible(true);
 
    // Now you can try our Swing Paint !!! Enjoy :D
  }
 
}