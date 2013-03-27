// eric tsai
// ttsai11 @smccd.edu
// CIS 255 WJ
// DrawPanel
// Program that uses classes
// to draw random shapes.
// Assignment #4
// 10/12/11
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.Color;//import stuff
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GradientPaint;
import java.util.Random;
import javax.swing.JPanel;
import java.awt.Graphics2D;
import java.awt.Font;
public class DrawPanel extends JPanel
{  //random number
   private Random randomNumbers = new Random();
   private int count = 200;//keep the count
   private boolean condition = false;//check first time move
   private int width, height, centerWidth, centerHeight, shapeType;
   private double design, stretchX, stretchY;
   private Color background;
   private MyShape shape;//super class for polymorphism use
   // constructor, creates a panel with random shapes
   public DrawPanel()
   {
      setBackground( Color.BLACK );//black background


      addMouseMotionListener(
      new MouseMotionAdapter()
      {
       	public void mouseMoved(MouseEvent event)
        {
        	if(condition == true)//if moved second time, shut down
        		System.exit(0);
       		else
       			condition = true;
       	}
      }
      );
   }
   private Color pickColor()
	{//make a nice looking color..
		int green = randomNumbers.nextInt( 256 );
        int red = randomNumbers.nextInt( 256 );
        int blue = randomNumbers.nextInt( 256 );
        green = (green + 255)/2;//average with white
       	blue = (blue + 255)/2;
      	red = (red + 255)/2;
     	Color color = new Color(red, green, blue);
    	return color;//return a good color
	}
   private Color dark()
	{//find something different..
		int green = randomNumbers.nextInt( 256 );
        int red = randomNumbers.nextInt( 256 );
        int blue = randomNumbers.nextInt( 256 );
        green = ( 255 - green)/2;//take the difference so
       	blue = (255 - blue)/2;	 //it would be blackish
      	red = (255 - red)/2;
     	Color color = new Color(red, green, blue);
    	return color;
	}
   private void houseKeeping()
   {
   		//generate different standard for each round of output
     		centerWidth = 0;//reset stuff
   			centerHeight= getHeight() /3;
  			design = 0;//use for random pattern of spiral
     		centerHeight += randomNumbers.nextInt(getHeight() / 3);//random coordinate
     		centerWidth += (randomNumbers.nextInt(2*getWidth()/3) + 100);
     		shapeType = randomNumbers.nextInt(10);//generate starting shape sequence
     		design = randomNumbers.nextInt(3) + 3;
     		stretchX = randomNumbers.nextInt(2) + 1;
     		stretchY = randomNumbers.nextInt(2) + 1;

   }

   @Override // override paintComponent
   public void paintComponent( Graphics g )
   {
   		Graphics2D g2 = (Graphics2D)g;//cast for 2D feture
		if(count >=0 && count < 100)
		{
			try {Thread.sleep((int)(-2.5 * count)+ 275); } catch (Exception e) {}
			//get radian and create spiral
     			Color color = pickColor();//make 2 colors
       			Color color2 = dark();
     			double radius =  3 * count + 5;
	     		double radian = count * (Math.PI/design + .1); //make it radian
	     		//change to x,y coordiante and shift to center
	     		int xPoint = (int)(stretchX * radius * Math.cos(radian)) + centerWidth;
	     		int yPoint = (int)(stretchY * radius * Math.sin(radian))+ centerHeight;

		      	switch ((count + shapeType)%3)//determine shape to draw
		      	{//make the shape, use count to get differnt size
	      			case 0:
	      				shape = new MyStar(xPoint, yPoint, color, count);
	      				break;
	      			case 1:
	      				shape = new MyOval(xPoint, yPoint, 0, 0, color, true, count);
	      				break;
	      			case 2:
	      				shape = new MyRectangle(xPoint, yPoint, 0, 0, color, true, count);
	      				break;
		      	}
		      	int a1 = randomNumbers.nextInt(100);//random for gradient color
	      	 	int b1 = randomNumbers.nextInt(100);
	       		int a2 = randomNumbers.nextInt(100);
	       		int b2 = randomNumbers.nextInt(100);
	      		if(shape.getX1() >= 0 && shape.getY1() >= 0)
	      		{
	      			g2.setPaint( new GradientPaint (a1,b1,color,a2,b2,color2,true));
	      			shape.draw(g2);
	      		}

		      	count++;
		}
		else if( count >= 100 && count < 101)
		{
			try {Thread.sleep(2000); } catch (Exception e) {}
			count++;
		}
		else if( count >= 101 && count < 200)
      	{
      		
      		try {Thread.sleep(25); } catch (Exception e) {}

     		//fill the screen with one blue rectangle
   			int size = (count - 100) * (getWidth() / 100);
	      	shape = new MyStar(centerWidth, centerHeight, background, size);
	      	MyStar mystar = (MyStar)shape;
	      	mystar.setSize(size);
	      	g2.setColor(Color.BLACK);
		    shape.draw(g2);
     		count++;
      	}
      	else
      	{//clear screen
			count = 0;
			houseKeeping();
		}//change speed of repaint w/ respect to count
	    repaint();//repaint, enjoy :)
   } // end method paintComponen

} // end class DrawPanel

