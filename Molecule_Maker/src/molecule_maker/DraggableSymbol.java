
package molecule_maker;

// DraggableSymbol.java
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class DraggableSymbol {
    
    public String symbol;
  
    public int x, y;
    public int width = 40, height = 40;
    public boolean dragging = false;// sürürklenebilir mi diye 
    public int offsetX, offsetY;// sürüklenme konumu
    public Cicle attachedCircle = null; // bağlı olduğu daire

    public DraggableSymbol(String symbol, int x, int y) {
        this.symbol = symbol;
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics g) {
        // bağlıysa daireye göre konumlandır
        if (attachedCircle != null) {
            x = attachedCircle.getX() + attachedCircle.getWight()/2 - width/4;
            y = attachedCircle.getY() + attachedCircle.getHeight()/2 - height/2;
        }
        g.setColor(Color.BLACK);
        g.drawString(symbol.toUpperCase(), x + width/4, y + height/2 + 5);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
    
    
    
    
  
    public Atom attachedAtom = null;   // bu sembol hangi Atom ile ilişkili
    
}

