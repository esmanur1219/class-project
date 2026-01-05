
package molecule_maker;


// Cicle.java
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
// ekrandaki sürürklenebilir daireyi temsil eder 
public class Cicle {
   private int x;
   private int y;
   private int wight;
   private int height;
   private Color color;

    public Cicle(int x, int y, int wight, int height, Color color) {
        this.x = x;
        this.y = y;
        this.wight = wight;
        this.height = height;
        this.color=color;
    }

    public void dwac(Graphics g){
       g.setColor(color);
       g.fillOval(x, y, wight, height);
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getWight() { return wight; }
    public int getHeight() { return height; }
    public Color getColor() { return color; }

    
    // dairenin hareketini belirler nere gideceig 
    public void move(int dx, int dy) {
        x += dx;
        y += dy;
    }
// dikdörtgen gibi düşünüyor ve ona fgöre hesaplama yapıypr 
    public boolean hitTest(Point p) {
        return p.x >= x && p.x <= x + wight &&
               p.y >= y && p.y <= y + height;
    }
}


