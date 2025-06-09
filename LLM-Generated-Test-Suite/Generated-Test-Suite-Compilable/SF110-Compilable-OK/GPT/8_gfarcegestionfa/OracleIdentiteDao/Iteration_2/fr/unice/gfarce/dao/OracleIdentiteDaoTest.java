package fr.unice.gfarce.dao;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

import javax.persistence.*;
import java.util.*;

import fr.unice.gfarce.identity.*;

@RunWith(MockitoJUnitRunner.class)
public class OracleIdentiteDaoTest {

    @InjectMocks
    private OracleIdentiteDao dao;

    @Mock
    private EntityManager em;

    @Mock
    private EntityTransaction tx;

    @Mock
    private EntityManagerFactory emf;

    @Mock
    private Identite identite;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(em.getTransaction()).thenReturn(tx);
    }

    @Test
    public void testInsert() {
        doNothing().when(em).persist(any(Identite.class));
        dao.insert(identite, "testForm", Calendar.getInstance());
        verify(em).persist(identite);
        verify(tx).begin();
        verify(tx).commit();
    }

    @Test
    public void testUpdate() {
        when(em.find(Identite.class, identite.getId())).thenReturn(identite);
        doNothing().when(em).merge(any(Identite.class));
        dao.update(identite);
        verify(em).find(Identite.class, identite.getId());
        verify(em).merge(identite);
        verify(tx).begin();
        verify(tx).commit();
    }

    @Test
    public void testDelete() {
        when(em.find(Identite.class, identite.getId())).thenReturn(identite);
        doNothing().when(em).remove(any(Identite.class));
        dao.delete(identite);
        verify(em).find(Identite.class, identite.getId());
        verify(em).remove(identite);
        verify(tx).begin();
        verify(tx).commit();
    }

    @Test
    public void testFind() {
        String queryStr = "select ident from Identite as ident where ident.nom like '%' and ident.prenom like '%' and ident.sex like '%' and ident.type like '%'";
        Query query = mock(Query.class);
        when(em.createQuery(queryStr)).thenReturn(query);
        when(query.getResultList()).thenReturn(Collections.emptyList());

        List<Identite> result = dao.find(null, null, null, null, null);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testFindCandidat() {
        List<Identite> identites = new ArrayList<>();
        identites.add(identite);
        when(identite.getType()).thenReturn(Identite.TypeIdentite.CANDIDAT);
        when(em.createQuery(anyString())).thenReturn(mock(Query.class));
        when(em.createQuery(anyString()).getResultList()).thenReturn(identites);

        Candidat[] result = dao.findCandidat(null, null, null, null);
        assertNotNull(result);
        assertEquals(1, result.length);
    }

    @Test
    public void testFindRespForm() {
        List<Identite> identites = new ArrayList<>();
        identites.add(identite);
        when(identite.getType()).thenReturn(Identite.TypeIdentite.FORMATEUR);
        when(em.createQuery(anyString())).thenReturn(mock(Query.class));
        when(em.createQuery(anyString()).getResultList()).thenReturn(identites);

        RespFormation[] result = dao.findRespForm(null, null, null, null);
        assertNotNull(result);
        assertEquals(1, result.length);
    }
}