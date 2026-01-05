
package molecule_maker;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;




public class Molecule_Maker {



    public static void main(String[] args) {
        JFrame frame = new JFrame("Molekül Oyunu");
        frame.setSize(1000, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GamePanel panel = new GamePanel();
             
        JButton check = new JButton("Check");
      
       
check.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
               panel.checkAndMoveNextMolecule();
            }


});
        frame.setLayout(null);
        check.setBounds(800, 50, 150, 40);
        frame.add(check);
        panel.setBounds(0, 0, 1000, 800);
        
      
        
     frame.add(panel);
        frame.setVisible(true);
    }
}

