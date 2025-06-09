package fr.unice.gfarce.dao;

import fr.unice.gfarce.identity.Identite;
import fr.unice.gfarce.identity.Candidat;
import fr.unice.gfarce.identity.RespFormation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Calendar;
import java.util.List;
import static org.junit.Assert.*;

public class OracleIdentiteDaoTest {

    private OracleIdentiteDao dao;
    private EntityManagerFactory emf;
    private EntityManager em;
    private EntityTransaction tx;

    @Before
    public void setUp() {
        dao = new OracleIdentiteDao();
        emf = Persistence.createEntityManagerFactory("Identite");
        em = emf.createEntityManager();
        tx = em.getTransaction();
    }

    @After
    public void tearDown() {
        if (em != null) {
            em.close();
        }
        if (emf != null) {
            emf.close();
        }
    }

    @Test
    public void testInsert() {
        Identite identite = new Identite();
        identite.setNom("Doe");
        identite.setPrenom("John");
        identite.setEmail("john.doe@example.com");
        identite.setSex("M");
        identite.setType(Identite.TypeIdentite.CANDIDAT);

        dao.insert(identite, "Form1", Calendar.getInstance());

        tx.begin();
        Identite foundIdentite = em.find(Identite.class, identite.getId());
        tx.commit();

        assertNotNull(foundIdentite);
        assertEquals("Doe", foundIdentite.getNom());
        assertEquals("John", foundIdentite.getPrenom());
        assertEquals("john.doe@example.com", foundIdentite.getEmail());
        assertEquals("M", foundIdentite.getSex());
        assertEquals(Identite.TypeIdentite.CANDIDAT, foundIdentite.getType());
    }

    @Test
    public void testUpdate() {
        Identite identite = new Identite();
        identite.setNom("Doe");
        identite.setPrenom("John");
        identite.setEmail("john.doe@example.com");
        identite.setSex("M");
        identite.setType(Identite.TypeIdentite.CANDIDAT);

        tx.begin();
        em.persist(identite);
        tx.commit();

        identite.setEmail("john.updated@example.com");
        dao.update(identite);

        tx.begin();
        Identite updatedIdentite = em.find(Identite.class, identite.getId());
        tx.commit();

        assertEquals("john.updated@example.com", updatedIdentite.getEmail());
    }

    @Test
    public void testDelete() {
        Identite identite = new Identite();
        identite.setNom("Doe");
        identite.setPrenom("John");
        identite.setEmail("john.doe@example.com");
        identite.setSex("M");
        identite.setType(Identite.TypeIdentite.CANDIDAT);

        tx.begin();
        em.persist(identite);
        tx.commit();

        dao.delete(identite);

        tx.begin();
        Identite deletedIdentite = em.find(Identite.class, identite.getId());
        tx.commit();

        assertNull(deletedIdentite);
    }

    @Test
    public void testFind() {
        Identite identite = new Identite();
        identite.setNom("Doe");
        identite.setPrenom("John");
        identite.setEmail("john.doe@example.com");
        identite.setSex("M");
        identite.setType(Identite.TypeIdentite.CANDIDAT);

        tx.begin();
        em.persist(identite);
        tx.commit();

        List<Identite> resultList = dao.find("Doe", "John", "M", "john.doe@example.com", Identite.TypeIdentite.CANDIDAT);

        assertNotNull(resultList);
        assertEquals(1, resultList.size());
        assertEquals("Doe", resultList.get(0).getNom());
        assertEquals("John", resultList.get(0).getPrenom());
        assertEquals("john.doe@example.com", resultList.get(0).getEmail());
        assertEquals("M", resultList.get(0).getSex());
        assertEquals(Identite.TypeIdentite.CANDIDAT, resultList.get(0).getType());
    }

    @Test
    public void testFindCandidat() {
        Identite identite = new Identite();
        identite.setNom("Doe");
        identite.setPrenom("John");
        identite.setEmail("john.doe@example.com");
        identite.setSex("M");
        identite.setType(Identite.TypeIdentite.CANDIDAT);

        tx.begin();
        em.persist(identite);
        tx.commit();

        Candidat[] result = dao.findCandidat("Doe", "John", "M", "john.doe@example.com");

        assertNotNull(result);
        assertEquals(1, result.length);
        assertEquals("Doe", result[0].getNom());
        assertEquals("John", result[0].getPrenom());
        assertEquals("john.doe@example.com", result[0].getEmail());
        assertEquals("M", result[0].getSex());
        assertEquals(Identite.TypeIdentite.CANDIDAT, result[0].getType());
    }

    @Test
    public void testFindRespForm() {
        Identite identite = new Identite();
        identite.setNom("Doe");
        identite.setPrenom("John");
        identite.setEmail("john.doe@example.com");
        identite.setSex("M");
        identite.setType(Identite.TypeIdentite.FORMATEUR);

        tx.begin();
        em.persist(identite);
        tx.commit();

        RespFormation[] result = dao.findRespForm("Doe", "John", "M", "john.doe@example.com");

        assertNotNull(result);
        assertEquals(1, result.length);
        assertEquals("Doe", result[0].getNom());
        assertEquals("John", result[0].getPrenom());
        assertEquals("john.doe@example.com", result[0].getEmail());
        assertEquals("M", result[0].getSex());
        assertEquals(Identite.TypeIdentite.FORMATEUR, result[0].getType());
    }
}