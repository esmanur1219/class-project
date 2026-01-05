
package molecule_maker;


// GamePanel.java
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class GamePanel extends JPanel implements MouseListener, MouseMotionListener {

    Peiyodik_tablo t1 = new Peiyodik_tablo();
    Çerçeve çerçeve;
    Cicle c1; // yeni daire oluşturmak için
    ArrayList<Cicle> circles = new ArrayList<>();
    ArrayList<DraggableSymbol> symbols = new ArrayList<>();
    DraggableSymbol selectedSymbol = null;
    Cicle selectedCircle = null;
Atom selectedAtom = null;

    Line selectedLine = null;
    Line.HitType dragType = Line.HitType.NONE;
    Point lastmouse;

    ArrayList<Line> lines = new ArrayList<>();
Line l;
    ArrayList<Atom> atoms = new ArrayList<>();
    ArrayList<Bond> bonds = new ArrayList<>();

    Molecule target;

    double tolerance = 8;

   
    
    MoleculeLibrary library ;
MoleculeLibrary.MoleculeDefinition targetMolecule;

    
    public GamePanel() {
        addMouseListener(this);
        addMouseMotionListener(this);

        çerçeve = new Çerçeve(new Point(50, 100), new Point(50, 500), new Point(600, 500), new Point(600, 100));
        c1 = new Cicle(700, 150, 60, 60, Color.DARK_GRAY);

      
          library = new MoleculeLibrary(); // kütüphaneyi başlat
             targetMolecule = library.getMoleculeByName("H2O"); // H2O'yu hedef olarak seç

                 l=new Line(new Point(680, 300), new Point(680, 400), Color.DARK_GRAY);
        // bir başlangıç çizgi
        lines.add(l);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // periyodik tablo
        t1.draw(g);

        // çerçeve
        çerçeve.draw(g2);

          for (Bond b : bonds) {
               b.line.drawq(g2);
}

        // çizgiler
        l.drawq(g2);
        for (Line l : lines) l.drawq(g2);

        // daireler
          c1.dwac(g);
        for (Cicle c : circles) c.dwac(g);
      

        
        // semboller
        for (DraggableSymbol s : symbols) s.draw(g);

        // atomlar
        for (Atom a : atoms) a.draw(g);

        // hedef molekül
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.drawString("İstenen Molekül: " + targetMolecule.name, 50, 40);
        
        ///silme 1
        
      
    }


@Override
public void mouseClicked(MouseEvent e) {
    // Sağ tık ile silme
    if (SwingUtilities.isRightMouseButton(e)) {
        // 1️⃣ Daireler
        circles.removeIf(c -> c.hitTest(e.getPoint()));

        // 2️⃣ Semboller
        symbols.removeIf(s -> s.getBounds().contains(e.getPoint()));

        // 3️⃣ Atomlar
        atoms.removeIf(a -> a.contains(e.getPoint()));

        // 4️⃣ Çizgiler
        lines.removeIf(line -> line.hitTest(e.getPoint(), 8) != Line.HitType.NONE);
        // baglar 
         bonds.removeIf(b -> b.line.hitTest(e.getPoint(), 8) != Line.HitType.NONE);
        repaint();
        return;
    }

    // Sol tık ile diğer işlemler (sembol oluşturma, kopya alma, vs.)
    // 1️⃣ Periyodik tablodan sembol oluşturma
    for (Element el : t1.getElements()) {
        if (el.rect.contains(e.getX(), e.getY())) {// recet ekrandaki alanı 
            DraggableSymbol s = new DraggableSymbol(el.symbol, el.rect.x + 10, el.rect.y + 10);
            symbols.add(s);
            repaint();
            return;
        }
    }

    // 2️⃣ Ana daireye tıklayınca yeni kopya oluştur
    if (c1.hitTest(e.getPoint())) {
        Cicle r = new Cicle(c1.getX(), c1.getY(), c1.getWight(), c1.getHeight(), Color.YELLOW);
        circles.add(r);
        repaint();
        return;
    }

    // 3️⃣ Ana line’a tıklayınca yeni kopya oluştur
    Line2D line2d = new Line2D.Double(l.getA().x, l.getA().y, l.getB().x, l.getB().y);
    if (line2d.ptSegDist(e.getPoint()) <= 8) {
        Line yeni = new Line(new Point(l.getA().x, l.getA().y), new Point(l.getB().x, l.getB().y), Color.CYAN);
      
            lines.add(yeni);
         Point aPoint = yeni.getA();
          Point bPoint = yeni.getB();

repaint();
      return;
      
      // Yeni line eklendikten sonra
   

}
}

@Override
public void mousePressed(MouseEvent e) {
    int mx = e.getX();
    int my = e.getY();
    lastmouse = e.getPoint();

    // 1️⃣ Atom veya sembol sürükleme kontrolü
    for (DraggableSymbol s : symbols) {
        if (s.getBounds().contains(mx, my)) {
            selectedSymbol = s;
            s.dragging = true;
            s.offsetX = mx - s.x;
            s.offsetY = my - s.y;
            return;
        }
    }

    // 2️⃣ Normal daireler sürükleme (ana daire c1 sabit)
    for (Cicle c : circles) {
        if (c.hitTest(e.getPoint()) && c != c1) {
            selectedCircle = c;
            return;
        }
    }

    // 3️⃣ Line sürükleme (ana line l sabit)
    for (Line line : lines) {
        if (line == l) continue; // ana line sabit
        Line.HitType hit = line.hitTest(e.getPoint(), 8);
        if (hit != Line.HitType.NONE) {
            selectedLine = line;
            dragType = hit;
            return;
        }
    }

    // 4️⃣ Atom üzerine tıklama (Atom’u sürüklemek için)
    for (Atom a : atoms) {
        if (a.contains(e.getPoint())) {
            selectedCircle = a.circle; // Atom’un dairesini sürükle
            selectedSymbol = null;     // sembol sürükleme kapalı
            return;
        }
    }
}

@Override
public void mouseDragged(MouseEvent e) {
    int dx = e.getX() - lastmouse.x;
    int dy = e.getY() - lastmouse.y;

    // 1️⃣ Sembol sürükleme
    if (selectedSymbol != null && selectedSymbol.dragging) {
        if (selectedSymbol.attachedAtom != null) {
            // Atom ile bağlı ise sadece Atom’u taşı
            selectedSymbol.attachedAtom.circle.move(dx, dy);
        } else if (selectedSymbol.attachedCircle != null) {
            // Sembol bağlı daire ile birlikte taşı
            selectedSymbol.attachedCircle.move(dx, dy);
        } else {
            selectedSymbol.x += dx;
            selectedSymbol.y += dy;
        }
    }
    // 2️⃣ Daire sürükleme
    else if (selectedCircle != null) {
        selectedCircle.move(dx, dy);
    }
    // 3️⃣ Line sürükleme
    else if (selectedLine != null) {
        switch (dragType) {
            case BODY -> selectedLine.move(dx, dy);
            case POINT_A -> selectedLine.moveA(dx, dy);
            case POINT_B -> selectedLine.moveB(dx, dy);
        }
    }
if (selectedAtom != null) {
    // Atom ve bağlı sembolü birlikte taşı
    selectedAtom.circle.move(dx, dy);
    if (selectedAtom.symbol != null) {
        // sembolün koordinatını da güncelle
        for (DraggableSymbol s : symbols) {
            if (s.attachedAtom == selectedAtom) {
                s.x += dx;
                s.y += dy;
            }
        }
    }
}

    lastmouse = e.getPoint();
    repaint();
}




@Override
public void mouseReleased(MouseEvent e) {
    // 1️⃣ Sembol bırakma
    if (selectedSymbol != null) {
        selectedSymbol.dragging = false;

        // eğer bir daire üstüne bırakıldıysa atom oluştur
        for (Cicle c : circles) {
            if (c.hitTest(e.getPoint())) {
                selectedSymbol.attachedCircle = c;
                Atom a = new Atom(selectedSymbol.symbol, c.getX(), c.getY(),Color.MAGENTA);
                atoms.add(a);

                // Orijinal daire ve sembolü listelerden kaldır
                circles.remove(c);
                symbols.remove(selectedSymbol);
                break;
            }
        }
    }

// mouseReleased içinde, Bond oluşturma 
 if (selectedLine != null) {
        Point aPoint = selectedLine.getA();
        Point bPoint = selectedLine.getB();

        Atom atom1 = null;
        Atom atom2 = null;
        int threshold = 20; // uç noktaların atomlara yakınlık toleransı

        for (Atom a : atoms) {
            Point center = new Point(a.circle.getX() + a.circle.getWight()/2,
                                     a.circle.getY() + a.circle.getHeight()/2);
            if (aPoint.distance(center) <= threshold) atom1 = a;
            if (bPoint.distance(center) <= threshold) atom2 = a;
        }

if (atom1 != null && atom2 != null && atom1 != atom2) {
    // Önce aynı çift arasında zaten bond var mı kontrol et
    int existingCount = 0;
    for (Bond b : bonds) {
        if ((b.a == atom1 && b.b == atom2) || (b.a == atom2 && b.b == atom1)) {
            existingCount++;
        }
    }

    // Eğer zaten 1 tane varsa (tekli bağ), ikinciye izin verme (H2O için)
    // Ama CO2, O2, N2 için izin vermeliyiz! O yüzden hedef moleküle göre karar verelim

    // Basit çözüm: H2O gibi tek bağ gereken moleküllerde engelle
    // Daha iyi çözüm: Hedef moleküldeki maksimum bağ sayısını aşmasına izin verme

    int bondCount = existingCount + 1;

    // Hedef moleküldeki bu çift için maksimum bağ sayısını bul
    String symA = atom1.symbol.toUpperCase();
    String symB = atom2.symbol.toUpperCase();
    String key = symA.compareTo(symB) < 0 ? symA + "-" + symB : symB + "-" + symA;

    int maxAllowed = 0;
    for (int[] bondIndices : targetMolecule.bonds) {
        String tA = targetMolecule.atoms.get(bondIndices[0]).toUpperCase();
        String tB = targetMolecule.atoms.get(bondIndices[1]).toUpperCase();
        String tKey = tA.compareTo(tB) < 0 ? tA + "-" + tB : tB + "-" + tA;
        if (tKey.equals(key)) maxAllowed++;
    }

    if (bondCount > maxAllowed) {
        // İzin verme, bond ekleme
        JOptionPane.showMessageDialog(this, 
            "Bu iki atom arasında fazla bağ çekemezsiniz!\nMaksimum: " + maxAllowed,
            "Fazla Bağ", JOptionPane.WARNING_MESSAGE);
        lines.remove(selectedLine); // çizgiyi sil
        selectedLine = null;
        repaint();
        return;
    }

    // Eğer uygunsa ekle
    Bond bond = new Bond(atom1, atom2, bondCount);
    bonds.add(bond);
    lines.remove(selectedLine);
    lines.add(bond.line);
}
selectedLine = null;
 }
 
 
    // Seçimleri sıfırla
    selectedAtom = null;
    selectedCircle = null;
    selectedSymbol = null;
    dragType = Line.HitType.NONE;

    repaint();
}



public boolean checkMolecule() {
    
    
      //ilk silinecek 
    if (!isFullyConnected()) {
    return false;
}
    if (!isMoleculeInsideFrame()) return false;

    // 1️⃣ Atom tür ve sayı kontrolü (bu kısım zaten doğru)
    HashMap<String, Integer> found = new HashMap<>();
    for (Atom a : atoms) {
        String sym = a.symbol.toUpperCase();
        found.put(sym, found.getOrDefault(sym, 0) + 1);
    }

    HashMap<String, Integer> targetCount = new HashMap<>();
    for (String s : targetMolecule.atoms) {
        String sym = s.toUpperCase();
        targetCount.put(sym, targetCount.getOrDefault(sym, 0) + 1);
    }

    if (!found.equals(targetCount)) return false;

    // 2️⃣ Bağların çokluğunu (multiplicity) kontrol et
    // Önce oyuncunun yaptığı bağları atom çiftlerine göre sayalım
    HashMap<String, Integer> playerBonds = new HashMap<>();

    for (Bond b : bonds) {
        String symA = b.a.symbol.toUpperCase();
        String symB = b.b.symbol.toUpperCase();
        
        // Sıra önemli değil → her zaman küçük harf sırasına göre anahtar yap
        String key = symA.compareTo(symB) < 0 ? symA + "-" + symB : symB + "-" + symA;
        
        playerBonds.put(key, playerBonds.getOrDefault(key, 0) + 1);
    }

    // Hedef molekülün bağlarını da aynı şekilde sayalım
    HashMap<String, Integer> targetBonds = new HashMap<>();

    for (int[] bondIndices : targetMolecule.bonds) {
        String symA = targetMolecule.atoms.get(bondIndices[0]).toUpperCase();
        String symB = targetMolecule.atoms.get(bondIndices[1]).toUpperCase();
        
        String key = symA.compareTo(symB) < 0 ? symA + "-" + symB : symB + "-" + symA;
        
        targetBonds.put(key, targetBonds.getOrDefault(key, 0) + 1);
    }

    // 3️⃣ İki harita tamamen aynı mı?
    if (!playerBonds.equals(targetBonds)) {
        return false;
    }

    return true;
}




  



public boolean isMoleculeInsideFrame() {
   int minX = Math.min(Math.min(çerçeve.getA().x, çerçeve.getB().x), 
                    Math.min(çerçeve.getC().x, çerçeve.getD().x));
int maxX = Math.max(Math.max(çerçeve.getA().x, çerçeve.getB().x), 
                    Math.max(çerçeve.getC().x, çerçeve.getD().x));
int minY = Math.min(Math.min(çerçeve.getA().y, çerçeve.getB().y), 
                    Math.min(çerçeve.getC().y, çerçeve.getD().y));
int maxY = Math.max(Math.max(çerçeve.getA().y, çerçeve.getB().y), 
                    Math.max(çerçeve.getC().y, çerçeve.getD().y));

    for (Atom a : atoms) {
        Point center = new Point(a.circle.getX() + a.circle.getWight()/2,
                                 a.circle.getY() + a.circle.getHeight()/2);
        if (center.x < minX || center.x > maxX || center.y < minY || center.y > maxY) {
            return false; // atom çerçevenin dışında
        }
    }
    return true; // tüm atomlar çerçeve içinde
}




public String getDetailedFeedback() {
    StringBuilder feedback = new StringBuilder();

    // 1. Çerçeve kontrolü
    if (!isMoleculeInsideFrame()) {
        feedback.append("⚠️ Some atoms are outside the frame!\\nMove all atoms inside the frame.\n\n");
    }

    // 2. Atom tür ve sayı kontrolü
    HashMap<String, Integer> found = new HashMap<>();
    for (Atom a : atoms) {
        String sym = a.symbol.toUpperCase();
        found.put(sym, found.getOrDefault(sym, 0) + 1);
    }

    HashMap<String, Integer> targetCount = new HashMap<>();
    for (String s : targetMolecule.atoms) {
        String sym = s.toUpperCase();
        targetCount.put(sym, targetCount.getOrDefault(sym, 0) + 1);
    }

    // Eksik atomlar
    for (String sym : targetCount.keySet()) {
        int needed = targetCount.get(sym);
        int has = found.getOrDefault(sym, 0);
        if (has < needed) {
            feedback.append("❌ Missing: ").append(needed - has).append(" item").append(sym).append("\n");
        }
    }

    // Fazla atomlar
    for (String sym : found.keySet()) {
        int has = found.get(sym);
        int needed = targetCount.getOrDefault(sym, 0);
        if (has > needed) {
            feedback.append("❌ Exstra: ").append(has - needed).append(" item").append(sym).append("\n");
        }
    }

    if (feedback.length() > 0) {
        feedback.append("\n");
    }

    // 3. Bağ kontrolü (eğer atomlar doğruysa daha detaylı bağ hatası verelim)
    if (found.equals(targetCount)) {
        // Atomlar doğruysa bağlara bakalım
        HashMap<String, Integer> playerBonds = new HashMap<>();
        for (Bond b : bonds) {
            String symA = b.a.symbol.toUpperCase();
            String symB = b.b.symbol.toUpperCase();
            String key = symA.compareTo(symB) < 0 ? symA + "-" + symB : symB + "-" + symA;
            playerBonds.put(key, playerBonds.getOrDefault(key, 0) + 1);
        }

        HashMap<String, Integer> targetBonds = new HashMap<>();
        for (int[] bondIndices : targetMolecule.bonds) {
            String symA = targetMolecule.atoms.get(bondIndices[0]).toUpperCase();
            String symB = targetMolecule.atoms.get(bondIndices[1]).toUpperCase();
            String key = symA.compareTo(symB) < 0 ? symA + "-" + symB : symB + "-" + symA;
            targetBonds.put(key, targetBonds.getOrDefault(key, 0) + 1);
        }

        // Eksik bağlar
        for (String key : targetBonds.keySet()) {
            int needed = targetBonds.get(key);
            int has = playerBonds.getOrDefault(key, 0);
            if (has < needed) {
                String[] parts = key.split("-");
                feedback.append("❌ Missing bonds: ").append(parts[0]).append(" with ").append(parts[1])
                        .append(" between ").append(needed - has).append(" more bonds are needed.\n");
            }
        }

        // Fazla bağlar
        for (String key : playerBonds.keySet()) {
            int has = playerBonds.get(key);
            int needed = targetBonds.getOrDefault(key, 0);
            if (has > needed) {
                String[] parts = key.split("-");
                feedback.append("❌ Extra bonds: ").append(parts[0]).append(" with").append(parts[1])
                        .append(" between").append(has - needed).append(" There are extra bonds\n");
            }
        }
    }

    if (feedback.length() == 0) {
        feedback.append("An unexpected error occurred. Please try again.");
    }

    return feedback.toString();
}

public void checkAndMoveNextMolecule() {
   
    if (checkMolecule() && isMoleculeInsideFrame()) {
        JOptionPane.showMessageDialog(this, "🎉  Congratulations!  " + targetMolecule.name + " The molecule is correct!", 
                                      "Succesful!", JOptionPane.INFORMATION_MESSAGE);

        // Sonraki moleküle geç...
        int currentIndex = library.molecules.indexOf(targetMolecule);
        if (currentIndex + 1 < library.molecules.size()) {
            targetMolecule = library.molecules.get(currentIndex + 1);
        } else {
            JOptionPane.showMessageDialog(this, "🏆   Congratulations! All molecules are complete!  " , 
                                          "Game Over", JOptionPane.INFORMATION_MESSAGE);
            return; // oyunu bitir
        }

        // Temizlik
        atoms.clear();
        bonds.clear();
        lines.clear();
        circles.clear();
        symbols.clear();
        repaint();

    } else {
        String feedback = getDetailedFeedback();
        JOptionPane.showMessageDialog(this, 
            "<html><pre>" + feedback + "</pre></html>",
        
                " not Corret yet  😔"
            ,JOptionPane.WARNING_MESSAGE);
    }
}


private boolean isFullyConnected() {
    if (atoms.isEmpty()) return true;

    HashSet<Atom> visited = new HashSet<>();
    if (atoms.isEmpty()) return true;

    ArrayDeque<Atom> queue = new ArrayDeque<>();
    queue.add(atoms.get(0));

    while (!queue.isEmpty()) {
        Atom current = queue.poll();
        if (!visited.add(current)) continue;

        for (Bond b : bonds) {
            if (b.a == current && !visited.contains(b.b)) queue.add(b.b);
            if (b.b == current && !visited.contains(b.a)) queue.add(b.a);
        }
    }

    return visited.size() == atoms.size();
}


    @Override
    public void mouseMoved(MouseEvent e) { }
    @Override
    public void mouseEntered(MouseEvent e) { }
    @Override
    public void mouseExited(MouseEvent e) { }
    
    
}

