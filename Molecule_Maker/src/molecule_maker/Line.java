
package molecule_maker;


// Line.java
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;

public class Line {
    private Point a;
    private Point b;
    private Color color;

    public Line(Point a, Point b, Color color) {
        this.a = a;
        this.b = b;
        this.color = color;
    }

    public Point getA() { return a; }
    public Point getB() { return b; }
    public Color getColor() { return color; }

    
    // neresinin sürürklendiggini bulmak için
    public enum HitType { NONE, BODY, POINT_A, POINT_B }

    public HitType hitTest(Point p, int tol) {
        if (p.distance(a) <= tol) return HitType.POINT_A;
        if (p.distance(b) <= tol) return HitType.POINT_B;

        Line2D l = new Line2D.Double(a, b);
        if (l.ptSegDist(p) <= tol) return HitType.BODY;

        return HitType.NONE;
    }

    public void move(int dx, int dy) { a.translate(dx, dy); b.translate(dx, dy); }
    public void moveA(int dx, int dy) { a.translate(dx, dy); }
    public void moveB(int dx, int dy) { b.translate(dx, dy); }

    public void drawq(Graphics2D g){
        g.setColor(color);
        g.setStroke(new BasicStroke(2));
        g.drawLine(a.x, a.y, b.x, b.y);
        g.fillOval(a.x - 5, a.y - 5, 10, 10);
        g.fillOval(b.x - 5, b.y - 5, 10, 10);
    }
}




