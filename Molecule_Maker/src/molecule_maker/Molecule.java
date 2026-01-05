
package molecule_maker;


// Molecule.java
import java.util.HashMap;
import java.util.Map;

public class Molecule {
    public String name;
    public Map<String, Integer> atoms;
    public int bondCount;

    public Molecule(String name, int bondCount) {
        this.name = name;
        this.bondCount = bondCount;
        atoms = new HashMap<>();
    }

    public void addAtom(String symbol, int count) {
        atoms.put(symbol, count);
    }
}

