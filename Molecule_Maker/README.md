# Molecule Building Game

This project is an interactive *educational chemistry game* developed using *Java Swing*.  
The application allows users to construct molecules by selecting elements from a periodic table, creating atoms, and forming chemical bonds between them.

---

## Purpose of the Project

The purpose of this project is to help students better understand:
- Atomic structures
- Chemical bonding rules
- Molecular composition and connectivity

By providing a visual and interactive environment, the project supports learning through practice.

---

## Technologies Used

- Java (JDK 8 or higher)
- Java Swing
- AWT (Graphics and Mouse Events)
- Object-Oriented Programming (OOP)

---

## Game Mechanics

1. Elements are selected from the periodic table  
2. The selected element symbol is dragged onto the workspace  
3. An atom is created when the symbol is dropped onto a circle  
4. Bonds are formed by drawing lines between atoms  
5. The molecule must be placed inside the frame  
6. The structure is checked using the *Check* button  

---

## Application Structure

- *Atom*: Represents an atom with its symbol and visual circle  
- *Bond*: Represents chemical bonds (single, double, triple)  
- *Line*: Used to draw and manipulate bonds  
- *DraggableSymbol*: Enables drag-and-drop element interaction  
- *Cicle*: Visual representation of atoms  
- *Element*: Represents periodic table elements  
- *MoleculeLibrary*: Stores target molecule definitions  
- *GamePanel*: Manages game logic, rendering, and validation  

---

## Supported Molecules

- H₂O (Water)
- CO₂ (Carbon Dioxide)
- O₂ (Oxygen)
- N₂ (Nitrogen)

---

## Molecule Validation Rules

A molecule is considered correct if:
- Atom types and counts match the target molecule  
- Bond types and numbers are correct  
- All atoms are placed inside the frame  
- The molecule is fully connected  

---

## How to Run

1. Install Java JDK 8 or higher  
2. Open the project in a Java IDE  
3. Run the Z5.java file  

---

## Author

Esmanur Çulbasan  
Java & Object-Oriented Programming Course Project & Visual Programming
