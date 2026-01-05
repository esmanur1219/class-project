
package molecule_maker;


// Peiyodik_tablo.java
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Peiyodik_tablo {

    private Element[] elements = new Element[20];
    String[] symbols = {"h","he","li","be","b","c","n","o","f","ne","na","mg","al","si","p","s","cl","ar","k","ca"};
    int[] numbers = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20};
    String[] names = {"hidrojen","helyum","lityum","berilyum","bor","karbon","azot","oksijen","flor","neon",
                      "sodyum","magnezyum","alüminyum","silisyum","fosfor","kükürt","klor","argon","potasyum","kalsiyum"};
    Color[] colors = {
        new Color(135,206,235), new Color(255,182,193), new Color(144,238,144), new Color(255,228,181),
        new Color(255,160,122), new Color(173,216,230), new Color(240,230,140), new Color(221,160,221),
        new Color(255,105,180), new Color(255,215,0), new Color(199,21,133), new Color(244,164,96),
        new Color(70,130,180), new Color(210,105,30), new Color(238,232,170), new Color(250,128,114),
        new Color(147,112,219), new Color(176,224,230), new Color(255,140,0), new Color(152,251,152)
    };

    
    public Peiyodik_tablo() {
    int margin = 10;           // elementler arasındaki boşluk
    int elementWidth = 60;
    int elementHeight = 60;
    int startX = 50;           // soldan başlangıç
    int startY = 600;          // üstten başlangıç (pencereye sığacak şekilde)
    
    for (int i = 0; i < 20; i++) {
        int x = startX + (i % 10) * (elementWidth + margin); // 10 eleman yan yana
        int y = startY + (i / 10) * (elementHeight + margin); // üst 10 eleman bir satır, alt 10 eleman ikinci satır

        elements[i] = new Element(
            numbers[i], 
            symbols[i], 
            names[i], 
            x, 
            y, 
            elementWidth, 
            elementHeight, 
            colors[i]
        );
    }
}

    public void draw(Graphics g){
        for(Element el: elements){
           g.setColor(el.highlighted ? el.color.darker() : el.color);
           
            g.fillRect(el.rect.x, el.rect.y, el.rect.width, el.rect.height);
            g.setColor(Color.BLACK);
            g.drawRect(el.rect.x, el.rect.y, el.rect.width, el.rect.height);
            g.setFont(new Font("Arial",Font.BOLD,12));
            g.drawString(el.number+"", el.rect.x+5, el.rect.y+20);
            g.drawString(el.symbol, el.rect.x+5, el.rect.y+40);
            g.drawString(el.name, el.rect.x+5, el.rect.y+60);
        }
    }

    public Element[] getElements(){ return elements; }

    public Color[] getColors() {
        return colors;
    }
    
    
}


