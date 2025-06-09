package fr.unice.gfarce.interGraph;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ModifTableStockageTest {

    private ModifTableStockage modifTableStockage;
    private TableStockage tableStockage;

    @Before
    public void setUp() {
        tableStockage = new TableStockage(3, 3); // Assuming TableStockage constructor takes rows and columns
        tableStockage.setColumnName(0, "Col1");
        tableStockage.setColumnName(1, "Col2");
        tableStockage.setColumnName(2, "Col3");
        tableStockage.setValueAt("A1", 0, 0);
        tableStockage.setValueAt("A2", 0, 1);
        tableStockage.setValueAt("A3", 0, 2);
        tableStockage.setValueAt("B1", 1, 0);
        tableStockage.setValueAt("B2", 1, 1);
        tableStockage.setValueAt("B3", 1, 2);
        tableStockage.setValueAt("C1", 2, 0);
        tableStockage.setValueAt("C2", 2, 1);
        tableStockage.setValueAt("C3", 2, 2);

        modifTableStockage = new ModifTableStockage(tableStockage);
    }

    @Test
    public void testAjouterColonne() {
        TableStockage newTable = modifTableStockage.ajouterColonne("NewCol", String.class);
        assertEquals(4, newTable.getColumnCount());
        assertEquals("NewCol", newTable.getColumnName(3));
    }

    @Test
    public void testAjouterLigne() {
        TableStockage newTable = modifTableStockage.ajouterLigne();
        assertEquals(4, newTable.getRowCount());
        assertEquals("", newTable.getValueAt(3, 0));
        assertEquals(0.0, newTable.getValueAt(3, 1));
        assertEquals(false, newTable.getValueAt(3, 2));
    }

    @Test
    public void testSupprimerColonne() {
        TableStockage newTable = modifTableStockage.supprimerColonne(1);
        assertEquals(2, newTable.getColumnCount());
        assertEquals("Col1", newTable.getColumnName(0));
        assertEquals("Col3", newTable.getColumnName(1));
    }

    @Test
    public void testSupprimerLigne() {
        TableStockage newTable = modifTableStockage.supprimerLigne(1);
        assertEquals(2, newTable.getRowCount());
        assertEquals("A1", newTable.getValueAt(0, 0));
        assertEquals("C1", newTable.getValueAt(1, 0));
    }

    @Test
    public void testSeparerColonne() {
        tableStockage.setValueAt("A1 A2", 0, 0);
        TableStockage newTable = modifTableStockage.separerColonne(0);
        assertEquals(4, newTable.getColumnCount());
        assertEquals("A1", newTable.getValueAt(0, 0));
        assertEquals("A2", newTable.getValueAt(0, 1));
    }

    @Test
    public void testFusionnerColonnes() {
        TableStockage newTable = modifTableStockage.fusionnerColonnes(0);
        assertEquals(2, newTable.getColumnCount());
        assertEquals("Col1 Col2", newTable.getColumnName(0));
        assertEquals("A1 A2", newTable.getValueAt(0, 0));
    }

    @Test
    public void testColonneMail() {
        TableStockage newTable = modifTableStockage.colonneMail();
        assertEquals(4, newTable.getColumnCount());
        assertEquals("Envoyer E-Mail a:", newTable.getColumnName(3));
        assertEquals(true, newTable.getValueAt(0, 3));
    }

    @Test
    public void testSelectMail() {
        tableStockage.setColumnName(3, "Envoyer E-Mail a:");
        tableStockage.setValueAt(true, 0, 3);
        tableStockage.setValueAt(false, 1, 3);
        tableStockage.setValueAt(true, 2, 3);
        TableStockage newTable = modifTableStockage.selectMail();
        assertEquals(2, newTable.getRowCount());
        assertEquals("A1", newTable.getValueAt(0, 0));
        assertEquals("C1", newTable.getValueAt(1, 0));
    }
}