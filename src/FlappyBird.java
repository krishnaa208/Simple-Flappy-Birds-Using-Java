
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.Timer;

class Bird extends Rectangle{

	public Bird() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Bird(Dimension d) {
		super(d);
		// TODO Auto-generated constructor stub
	}

	public Bird(int x, int y, int width, int height) {
		super(x, y, width, height);
		// TODO Auto-generated constructor stub
	}

	public Bird(int width, int height) {
		super(width, height);
		// TODO Auto-generated constructor stub
	}

	public Bird(Point p, Dimension d) {
		super(p, d);
		// TODO Auto-generated constructor stub
	}

	public Bird(Point p) {
		super(p);
		// TODO Auto-generated constructor stub
	}

	public Bird(Rectangle r) {
		super(r);
		// TODO Auto-generated constructor stub
	}
	
}
public class FlappyBird implements ActionListener, MouseListener{
  
  public int score;
  public boolean gameOver;
  public boolean started = true;
  public static FlappyBird flappybird;
  public final int WIDTH = 800; 
  public final int HEIGHT = 800;
  public Render render;
  public Bird bird;
  public int ticks;
  public int yMotion;
  
  public ArrayList<Rectangle> columns;
  
  public Random random = new Random();
  
  public FlappyBird()
  {
    JFrame jframe = new JFrame();
    Timer timer = new Timer(20, this);
    
    render = new Render();
    
    jframe.add(render);
    jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    jframe.setSize(800, 800);
    jframe.setResizable(false);
    jframe.setVisible(true);
    jframe.setTitle("Flappy Birds");
    jframe.addMouseListener(this);
    
    bird = new Bird(400, 400, 20, 20);
    columns = new ArrayList();
    addcolumn(true);
    addcolumn(true);
    addcolumn(true);
    addcolumn(true);
    
    timer.start();
  }
  
  public void repaint(Graphics g) {
    g.setColor(Color.cyan);
    g.fillRect(0, 0, 800, 800);
    
    g.setColor(Color.RED);
    g.fillRect(bird.x, bird.y, bird.width, bird.height);
    
    g.setColor(Color.ORANGE);
    g.fillRect(0, 680, 800, 120);
    
    g.setColor(Color.green);
    g.fillRect(0, 680, 800, 20);
    
    for (Rectangle column : columns) {
      paintcolumn(g, column);
    }
    
    g.setColor(Color.white);
    g.setFont(new Font("Arial", 1, 100));
    
    if (gameOver) {
      g.drawString("Game Over !", 75, 400);
    }
    
    if ((!gameOver) && (started)) {
      g.drawString(String.valueOf(score), 375, 100);
    }
  }
   
  public void paintcolumn(Graphics g, Rectangle column) {
    g.setColor(Color.GREEN.darker().darker());
    g.fillRect(column.x, column.y, column.width, column.height);
  }
  
  public void jump()
  {
    if (gameOver){
      
      bird = new Bird(400, 400, 20, 20);
      columns.clear();
      yMotion = 0;
      score = 0;
      addcolumn(true);
      addcolumn(true);
      addcolumn(true);
      addcolumn(true);
      gameOver = false;
    }
    if (!started){
      started = true;
    }
    else if (!gameOver){
      if (yMotion > 0){
    	  yMotion = 0;
      }
      yMotion -= 10;
    }
  }
  
  public void addcolumn(boolean start)
  {
    int space = 300;
    int width = 100;
    int height = 50 + random.nextInt(300);
    if (start) {
      columns.add(new Rectangle(800 + width + columns.size() * 300, 800 - height - 120, width, height));
      columns.add(new Rectangle(800 + width + (columns.size() - 1) * 300, 0, width, 800 - height - space));
    } else {
      columns.add(new Rectangle(columns.get(columns.size() - 1).x + 600, 800 - height - 120, width, height));
      columns.add(new Rectangle(columns.get(columns.size() - 1).x, 0, width, 800 - height - space));
    }
  }
  
  public void actionPerformed(ActionEvent e)
  {
    int speed = 5;
    
    ticks += 1;
    
    if (started) {
      for (Rectangle column: columns) {
        column.x -= speed;
      }
      
      if ((ticks % 2 == 0) && (yMotion < 15)) {
    	  yMotion += 1;
      }
      
     
      for (int i =0 ; i < columns.size(); i++) { 
    	Rectangle column = columns.get(i);
        if (column.x + column.width < 0) {
        	columns.remove(column);
        	if (column.y == 0) {
        		addcolumn(false);
        	}
        }
      }
      bird.y += yMotion;
      
      for (Rectangle column1 : columns)
      {
        if ((column1.y == 0) && (bird.x + bird.width / 2 > column1.x + column1.width / 2 - 10) && (bird.x + bird.width / 2 < column1.x + column1.width / 2 + 10))
        {
          score += 1;
        }
        if (column1.intersects(bird)) {
          gameOver = true;
          bird.x = (bird.x - bird.width);
        }
      }
      

      if ((bird.y > 680) || (bird.y < 0))
      {
        gameOver = true;
      }
      if (gameOver) {
        bird.y = (680 - bird.height);
      }
    }
    render.repaint();
  }
  
  public void mouseClicked(MouseEvent arg0)
  {
    jump();
  }
  

  public static void main(String[] args)
  {
	  try {
		Thread.sleep(2000);
	} catch (InterruptedException e) {
		System.out.println("Exception in Main");
	}
    flappybird = new FlappyBird();
  }
  
  public void mouseEntered(MouseEvent arg0) {}
  
  public void mouseExited(MouseEvent arg0) {}
  
  public void mousePressed(MouseEvent arg0) {}
  
  public void mouseReleased(MouseEvent arg0) {}
}
