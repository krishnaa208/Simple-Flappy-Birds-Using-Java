
import javax.swing.JPanel;

public class Render extends JPanel
{
  private static final long serialVersionUID = 1L;
  
  public Render() {}
  
  protected void paintComponent(java.awt.Graphics g)
  {
    super.paintComponent(g);
    FlappyBird.flappybird.repaint(g);
  }
}
