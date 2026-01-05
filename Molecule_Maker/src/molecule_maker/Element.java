
package molecule_maker;


// Element.java
import java.awt.Color;
import java.awt.Rectangle;

public class Element {
    public int number;
    public String symbol;
    public String name;
    public Rectangle rect;
    public Color color;
    public boolean highlighted = false;
   // public boolean dragging = false;
    public int offsetX, offsetY;

    public Element(int number, String symbol, String name, int x, int y, int width, int height, Color color) {
        this.number = number;
        this.symbol = symbol;
        this.name = name;
        this.rect = new Rectangle(x, y, width, height);
        this.color = color;
    }
}

