package twitter4j;

import org.junit.Test;
import static org.junit.Assert.*;
import twitter4j.ExceptionDiagnosis;

public class ExceptionDiagnosisTest {

    @Test
    public void testConstructorWithThrowableOnly() {
        Throwable throwable = new Throwable();
        ExceptionDiagnosis diagnosis = new ExceptionDiagnosis(throwable);

        assertNotNull(diagnosis);
        assertEquals(0, diagnosis.getStackLineHash());
        assertEquals(0, diagnosis.getLineNumberHash());
        assertNotNull(diagnosis.asHexString());
    }

    @Test
    public void testConstructorWithThrowableAndInclusionFilter() {
        Throwable throwable = new Throwable();
        String[] inclusionFilter = {"java.lang"};
        ExceptionDiagnosis diagnosis = new ExceptionDiagnosis(throwable, inclusionFilter);

        assertNotNull(diagnosis);
        assertEquals(0, diagnosis.getStackLineHash());
        assertEquals(0, diagnosis.getLineNumberHash());
        assertNotNull(diagnosis.asHexString());
    }

    @Test
    public void testGetStackLineHash() {
        Throwable throwable = new Throwable();
        ExceptionDiagnosis diagnosis = new ExceptionDiagnosis(throwable);

        assertEquals(0, diagnosis.getStackLineHash());
    }

    @Test
    public void testGetLineNumberHash() {
        Throwable throwable = new Throwable();
        ExceptionDiagnosis diagnosis = new ExceptionDiagnosis(throwable);

        assertEquals(0, diagnosis.getLineNumberHash());
    }

    @Test
    public void testAsHexString() {
        Throwable throwable = new Throwable();
        ExceptionDiagnosis diagnosis = new ExceptionDiagnosis(throwable);

        assertNotNull(diagnosis.asHexString());
        assertTrue(diagnosis.asHexString().matches("[0-9a-f]{8}-[0-9a-f]{8}"));
    }

    @Test
    public void testEquals() {
        Throwable throwable1 = new Throwable();
        Throwable throwable2 = new Throwable();
        ExceptionDiagnosis diagnosis1 = new ExceptionDiagnosis(throwable1);
        ExceptionDiagnosis diagnosis2 = new ExceptionDiagnosis(throwable2);

        assertTrue(diagnosis1.equals(diagnosis1)); // reflexive
        assertFalse(diagnosis1.equals(null)); // null
        assertFalse(diagnosis1.equals(new Object())); // different class
        assertTrue(diagnosis1.equals(diagnosis2)); // different instances, same content
    }

    @Test
    public void testHashCode() {
        Throwable throwable1 = new Throwable();
        Throwable throwable2 = new Throwable();
        ExceptionDiagnosis diagnosis1 = new ExceptionDiagnosis(throwable1);
        ExceptionDiagnosis diagnosis2 = new ExceptionDiagnosis(throwable2);

        assertEquals(diagnosis1.hashCode(), diagnosis2.hashCode());
    }

    @Test
    public void testToString() {
        Throwable throwable = new Throwable();
        ExceptionDiagnosis diagnosis = new ExceptionDiagnosis(throwable);

        String expected = "ExceptionDiagnosis{stackLineHash=0, lineNumberHash=0}";
        assertEquals(expected, diagnosis.toString());
    }
}