
package molecule_maker;


// Atom.java
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class Atom {
    // molekül sınıfıf için laızm ayrı ayrı ikki farklı nesneyele ugraşamak yerine bir nesne ile ilgilenmek için
    public String symbol;
    public Cicle circle;
// otom oluştugunda otamatik oluşması için
    public Atom(String symbol, int x, int y, Color color) {
        this.symbol = symbol;
        circle = new Cicle(x, y, 50, 50, color);
    }
//çizzimi önce dairesi sonra sembolu 
    public void draw(Graphics g) {
        circle.dwac(g);
        g.setColor(Color.BLACK);
        g.drawString(symbol.toUpperCase(),
                circle.getX() + 18,
                circle.getY() + 30);
    }
// fare cirlenın üstündemi daire sınıfında çektik 
    public boolean contains(Point p) {
        return circle.hitTest(p);
    }
}





