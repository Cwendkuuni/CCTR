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
        tableStockage.setColumnName(0, "Column1");
        tableStockage.setColumnName(1, "Column2");
        tableStockage.setColumnName(2, "Column3");
        tableStockage.setValueAt("Value1", 0, 0);
        tableStockage.setValueAt("Value2", 0, 1);
        tableStockage.setValueAt("Value3", 0, 2);
        tableStockage.setValueAt("Value4", 1, 0);
        tableStockage.setValueAt("Value5", 1, 1);
        tableStockage.setValueAt("Value6", 1, 2);
        tableStockage.setValueAt("Value7", 2, 0);
        tableStockage.setValueAt("Value8", 2, 1);
        tableStockage.setValueAt("Value9", 2, 2);

        modifTableStockage = new ModifTableStockage(tableStockage);
    }

    @Test
    public void testAjouterColonne() {
        TableStockage newTable = modifTableStockage.ajouterColonne("NewColumn", String.class);
        assertEquals(4, newTable.getColumnCount());
        assertEquals("NewColumn", newTable.getColumnName(3));
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
        assertEquals("Column1", newTable.getColumnName(0));
        assertEquals("Column3", newTable.getColumnName(1));
    }

    @Test
    public void testSupprimerLigne() {
        TableStockage newTable = modifTableStockage.supprimerLigne(1);
        assertEquals(2, newTable.getRowCount());
        assertEquals("Value1", newTable.getValueAt(0, 0));
        assertEquals("Value7", newTable.getValueAt(1, 0));
    }

    @Test
    public void testSeparerColonne() {
        tableStockage.setColumnClass(1, String.class);
        tableStockage.setValueAt("Value2 Value2b", 0, 1);
        TableStockage newTable = modifTableStockage.separerColonne(1);
        assertEquals(4, newTable.getColumnCount());
        assertEquals("Value2", newTable.getValueAt(0, 1));
        assertEquals("Value2b", newTable.getValueAt(0, 2));
    }

    @Test
    public void testFusionnerColonnes() {
        TableStockage newTable = modifTableStockage.fusionnerColonnes(0);
        assertEquals(2, newTable.getColumnCount());
        assertEquals("Column1 Column2", newTable.getColumnName(0));
        assertEquals("Value1 Value2", newTable.getValueAt(0, 0));
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
        assertEquals("Value1", newTable.getValueAt(0, 0));
        assertEquals("Value7", newTable.getValueAt(1, 0));
    }
}