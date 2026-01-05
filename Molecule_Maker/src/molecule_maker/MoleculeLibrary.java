
package molecule_maker;



import java.util.ArrayList;
import java.util.List;

public class MoleculeLibrary {

    // Molekül tanımı
    public static class MoleculeDefinition {
        public String name;
        public List<String> atoms;   // atom sembolleri
        public List<int[]> bonds;    // her bağ için atom indexleri

        public MoleculeDefinition(String name) {
            this.name = name;
            atoms = new ArrayList<>();
            bonds = new ArrayList<>();
        }

        public void addAtom(String symbol) {
            atoms.add(symbol.toUpperCase());
        }

        // iki atom indexi arasına bağ ekle
        public void addBond(int indexA, int indexB) {
            bonds.add(new int[]{indexA, indexB});
        }
    }

    public List<MoleculeDefinition> molecules;

    public MoleculeLibrary() {
        molecules = new ArrayList<>();
        initMolecules();
    }

    private void initMolecules() {
        // H2O
        MoleculeDefinition water = new MoleculeDefinition("H2O");
        water.addAtom("O"); // 0
        water.addAtom("H"); // 1
        water.addAtom("H"); // 2
        water.addBond(0, 1);
        water.addBond(0, 2);
        molecules.add(water);

        // CO2 (çift bağlar)
        MoleculeDefinition co2 = new MoleculeDefinition("CO2");
        co2.addAtom("C"); // 0
        co2.addAtom("O"); // 1
        co2.addAtom("O"); // 2
        // çift bağ: iki kez addBond ekliyoruz
        co2.addBond(0, 1);
        co2.addBond(0, 1);
        co2.addBond(0, 2);
        co2.addBond(0, 2);
        molecules.add(co2);

        // O2 (çift bağ)
        MoleculeDefinition o2 = new MoleculeDefinition("O2");
        o2.addAtom("O"); // 0
        o2.addAtom("O"); // 1
        o2.addBond(0, 1);
        o2.addBond(0, 1); // çift bağ
        molecules.add(o2);

        // N2 (üçlü bağ)
        MoleculeDefinition n2 = new MoleculeDefinition("N2");
        n2.addAtom("N"); // 0
        n2.addAtom("N"); // 1
        n2.addBond(0, 1);
        n2.addBond(0, 1);
        n2.addBond(0, 1); // üçlü bağ
        molecules.add(n2);

      
    }

    public MoleculeDefinition getMoleculeByName(String name) {
        for (MoleculeDefinition m : molecules) {
            if (m.name.equalsIgnoreCase(name)) return m;
        }
        return null;
    }
}
