
package molecule_maker;


import java.awt.Color;
import java.awt.Point;

public class Bond {
    public Atom a;
    public Atom b;
    public Line line; // Mevcut sistemi bozmamak için tek Line tutuyor

    public Bond(Atom a, Atom b, int bondCount) {
        this.a = a;
        this.b = b;

        // Hesapla: ortadaki çizgi + paralel offset
        int x1 = a.circle.getX() + 25;
        int y1 = a.circle.getY() + 25;
        int x2 = b.circle.getX() + 25;
        int y2 = b.circle.getY() + 25;

        // Tekli bağ
        if (bondCount == 1) {
            line = new Line(new Point(x1, y1), new Point(x2, y2), Color.BLACK);
            return;
        }

        // Çift veya üçlü bağ: hafif paralel çizgi
        int spacing = 6; // piksel cinsinden paralel mesafe

        double dx = x2 - x1;
        double dy = y2 - y1;
        double length = Math.sqrt(dx*dx + dy*dy);
        double offsetX = -dy / length;
        double offsetY = dx / length;

        // Orta çizgi için shift = 0
        double shift = 0;
        if (bondCount == 2) shift = spacing / 2.0; // iki çizgi: -3, +3
        if (bondCount == 3) shift = spacing;       // üç çizgi: -6,0,+6

        // Sadece tek Line kullanacağız, ufak kaydırma uygulayalım
        line = new Line(
            new Point((int)(x1 - offsetX * (bondCount-1)/2.0 * spacing), 
                      (int)(y1 - offsetY * (bondCount-1)/2.0 * spacing)),
            new Point((int)(x2 - offsetX * (bondCount-1)/2.0 * spacing), 
                      (int)(y2 - offsetY * (bondCount-1)/2.0 * spacing)),
            Color.BLACK
        );
    }
} 



