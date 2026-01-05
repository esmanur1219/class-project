
package molecule_maker;



import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.BasicStroke;

public class Çerçeve {
    private Point a, b, c, d;
    public Çerçeve(Point a, Point b, Point c, Point d) { this.a=a; this.b=b; this.c=c; this.d=d; }
    public Point getA() { return a; } public Point getB() { return b; } public Point getC() { return c; } public Point getD() { return d; }

    public void draw(Graphics2D g) {
        g.setStroke(new BasicStroke(2));
        g.drawLine(a.x,a.y,b.x,b.y);
        g.drawLine(b.x,b.y,c.x,c.y);
        g.drawLine(c.x,c.y,d.x,d.y);
        g.drawLine(d.x,d.y,a.x,a.y);
    }

    
}

